import React, { useState, useEffect } from 'react';
import { useLocation } from '@reach/router';
import { navigate } from 'gatsby';
import { useSelector } from 'react-redux';
import {
  Box,
  Container,
  Typography,
  Paper,
  Grid,
  TextField,
  Button,
  Chip,
  Stack,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  IconButton,
  Divider,
  Alert,
  GridSize,
} from '@mui/material';
import { Add as AddIcon, Delete as DeleteIcon, Edit as EditIcon } from '@mui/icons-material';
import { useGetCurrentProfileQuery, useUpdateProfileMutation } from '../services/profile.service';
import { RootState } from '../state/store';
import { Profile, TimeSlot, PriceRange, ProfileUpdateRequest } from '../types/profile';
import Layout from '../components/Layout';

const DAYS_OF_WEEK = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

const ProfilePage: React.FC = () => {
  const location = useLocation();
  const userInfo = useSelector((state: RootState) => state.auth.userInfo);
  
  // Update the query to include email parameter
  const { data: profile, isLoading, error } = useGetCurrentProfileQuery(
    userInfo?.email_address ?? '', // Pass email address
    {
      skip: !userInfo?.email_address, // Skip if no email address available
    }
  );


  const [updateProfile] = useUpdateProfileMutation();

  // Redirect to login if no user is found
  useEffect(() => {
    if (!userInfo) {
      navigate('/login');
    }
  }, [userInfo]);

  const [isEditing, setIsEditing] = useState(false);
  const [editedProfile, setEditedProfile] = useState<Profile | null>(null);

  useEffect(() => {
    if (profile) {
      setEditedProfile({
        ...profile,
        interestedSubjects: profile.interestedSubjects || [],
        topics: profile.topics || [],
        teachingSubjects: profile.teachingSubjects || '',
        teachingAvailability: profile.teachingAvailability || [],
        minBudget: profile.minBudget || 0,
        maxBudget: profile.maxBudget || 0,
        hourlyRate: profile.hourlyRate || 0,
      } as Profile);
    }
  }, [profile]);

  const handleEdit = () => {
    if (profile) {
      setEditedProfile({
        ...profile,
        interestedSubjects: profile.interestedSubjects || [],
        topics: profile.topics || [],
        teachingSubjects: profile.teachingSubjects || '',
        teachingAvailability: profile.teachingAvailability || [],
        minBudget: profile.minBudget || 0,
        maxBudget: profile.maxBudget || 0,
        hourlyRate: profile.hourlyRate || 0,
      } as Profile);
      setIsEditing(true);
    }
  };

  const handleSave = async () => {
    if (profile?.userId && editedProfile) {
      const updateData: ProfileUpdateRequest = {
        subjects: Array.isArray(editedProfile.teachingSubjects)
          ? editedProfile.teachingSubjects
          : (editedProfile.teachingSubjects ? editedProfile.teachingSubjects.split(',').map(s => s.trim()) : []),
        interests: editedProfile.interestedSubjects || [],
        topics: editedProfile.topics || [],

        hourlyRate: editedProfile.hourlyRate,
        experience: typeof editedProfile.experience === 'string' ? editedProfile.experience : '',
        education: typeof editedProfile.education === 'string' ? editedProfile.education : '',
        bio: typeof editedProfile.bio === 'string' ? editedProfile.bio : '',
        availability: []
      };
      await updateProfile({ userId: profile.userId, data: updateData });
      setIsEditing(false);
    }
  };

 
  const handleTimeSlotChange = (index: number, field: keyof TimeSlot, value: string | number) => {
    if (editedProfile) {
      const newTimeSlots = [...editedProfile.teachingAvailability];
      newTimeSlots[index] = { ...newTimeSlots[index], [field]: value };
      setEditedProfile({ ...editedProfile, availability: newTimeSlots } as Profile);
    }
  };

  const addTimeSlot = () => {
    if (editedProfile) {
      const newTimeSlot: TimeSlot = {
        id: Date.now().toString(),
        startTime: '09:00',
        endTime: '10:00',
        dayOfWeek: 1,
      };
      setEditedProfile({
        ...editedProfile,
        availability: [...editedProfile.teachingAvailability, newTimeSlot],
      } as Profile);
    }
  };

  const removeTimeSlot = (index: number) => {
    if (editedProfile) {
      const newTimeSlots = [...editedProfile.teachingAvailability];
      newTimeSlots.splice(index, 1);
      setEditedProfile({ ...editedProfile, availability: newTimeSlots } as Profile);
    }
  };

  if (error) {
    return (
      <Layout isLoading={false}>
        <Container maxWidth="md" sx={{ py: 4 }}>
          <Alert severity="error">Failed to load profile. Please try again later.</Alert>
        </Container>
      </Layout>
    );
  }

  if (isLoading || !profile || !editedProfile) {
    return (
      <Layout isLoading={true}>
        <Container maxWidth="md" sx={{ py: 4 }}>
          <Typography>Loading profile...</Typography>
        </Container>
      </Layout>
    );
  }

  const isStudent = profile.role === 'STUDENT';

  return (
    <Layout isLoading={isLoading}>
      <Container sx={{ ml: '240px' }}>
        <Paper sx={{ p: 2,mb:4 }}>
         
          <Box sx={{ mb: 4 }}>
            <Typography variant="h4" gutterBottom>
              {profile?.role === 'STUDENT' ? 'Student Profile' : 'Tutor Profile'}
            </Typography>
            <Grid container spacing={3} sx={{ display: 'flex', alignItems: 'center' }}>
              <Grid >
                <TextField
                  fullWidth
                  label="Email"
                  value={profile?.email || ''}
                  disabled
                />
              </Grid>
              <Grid >
                <TextField
                  fullWidth
                  label="Full Name"
                  value={profile?.fullName || ''}
                  disabled
                />
              </Grid>
              <Grid >
                <Button
                  variant="contained"
                  color="primary"
                  startIcon={<EditIcon />}
                  onClick={handleEdit}
                  sx={{ height: '56px' }}
                >
                  Edit Profile
                </Button>
              </Grid>
            </Grid>
          </Box>

          {/* Profile Details */}
          <Box sx={{ mb: 4 }}>
            <Typography variant="h6" gutterBottom>Profile Information</Typography>
            <Box sx={{ mb: 4 }}></Box>
              <Grid>
                <TextField
                  fullWidth
                  multiline
                  rows={4}
                  label="Bio"
                  value={profile?.bio || ''}
                  disabled
                />
              </Grid>
              </Box>
              <Box sx={{ mb: 4 }}>
              <Grid >
                <TextField
                  fullWidth
                  multiline
                  rows={4}
                  label="Education Background"
                  value={profile?.education || ''}
                  disabled
                />
              </Grid>
              </Box>
              <Box sx={{ mb: 4 }}>
              <Grid>
                <TextField
                  multiline
                  rows={4}
                  label={profile?.role === 'STUDENT' ? 'Class Level' : 'Experience'}
                  value={profile?.experience || ''}
                  disabled
                  sx={{ width: '50%' }}
                />
             
          
            <Typography variant="h6" gutterBottom>
              {profile?.role === 'STUDENT' ? 'Interested Subjects' : 'Teaching Subjects'}
            </Typography>
            <TextField
              sx={{ width: '50%' }}
              multiline
              rows={2}
              label={profile?.role === 'STUDENT' ? 'Interested Subjects' : 'Teaching Subjects'}
              value={profile?.role === 'STUDENT' 
                ? profile?.interestedSubjects?.join(', ') 
                : profile?.teachingSubjects}
              disabled
            />
             </Grid>
          </Box>

          {/* Budget/Rate Information */}
          {profile?.role === 'STUDENT' ? (
            <Box sx={{ mb: 4 }}>
              <Typography variant="h6" gutterBottom>Budget Range</Typography>
              <Grid container spacing={2}>
                <Grid>
                  <TextField
                    fullWidth
                    label="Minimum Budget"
                    type="number"
                    value={profile?.minBudget || 0}
                    disabled
                  />
                </Grid>
                <Grid>
                  <TextField
                    fullWidth
                    label="Maximum Budget"
                    type="number"
                    value={profile?.maxBudget || 0}
                    disabled
                  />
                </Grid>
              </Grid>
            </Box>
          ) : (
            <Box sx={{ mb: 4 }}>
              <Typography variant="h6" gutterBottom>Hourly Rate</Typography>
              <TextField
                fullWidth
                label="Rate per Hour"
                type="number"
                value={profile?.hourlyRate || 0}
                disabled
              />
            </Box>
          )}
        </Paper>
      </Container>
    </Layout>
  );
};

export default ProfilePage;
