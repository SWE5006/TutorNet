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
import { Add as AddIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { useGetCurrentProfileQuery, useUpdateProfileMutation } from '../services/profile.service';
import { RootState } from '../state/store';
import { Profile, TimeSlot, PriceRange, ProfileUpdateRequest } from '../types/profile';
import Layout from '../components/Layout';

const DAYS_OF_WEEK = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

const ProfilePage: React.FC = () => {
  const location = useLocation();
  const userInfo = useSelector((state: RootState) => state.auth.userInfo);
  
  const { data: profile, isLoading, error } = useGetCurrentProfileQuery();
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
        subjects: profile.subjects || [],
        interests: profile.interests || [],
        topics: profile.topics || [],
        availability: profile.availability || [],
        priceRange: profile.priceRange || { min: 0, max: 0 },
        hourlyRate: profile.hourlyRate || 0,
      } as Profile);
    }
  }, [profile]);

  const handleEdit = () => {
    if (profile) {
      setEditedProfile({
        ...profile,
        subjects: profile.subjects,
        interests: profile.interests,
        topics: profile.topics,
        availability: profile.availability,
        priceRange: profile.priceRange || { min: 0, max: 0 },
        hourlyRate: profile.hourlyRate,
      } as Profile);
      setIsEditing(true);
    }
  };

  const handleSave = async () => {
    if (profile?.userId && editedProfile) {
      const updateData: ProfileUpdateRequest = {
        subjects: editedProfile.subjects?.map(s => s.name) || [],
        interests: editedProfile.interests || [],
        topics: editedProfile.topics || [],
        availability: editedProfile.availability || [],
        priceRange: editedProfile.priceRange ? {
          min: editedProfile.priceRange.min || 0,
          max: editedProfile.priceRange.max || 0
        } : undefined,
        hourlyRate: editedProfile.hourlyRate,
        experience: typeof editedProfile.experience === 'string' ? editedProfile.experience : '',
        education: typeof editedProfile.education === 'string' ? editedProfile.education : '',
        bio: typeof editedProfile.bio === 'string' ? editedProfile.bio : ''
      };
      await updateProfile({ userId: profile.userId, data: updateData });
      setIsEditing(false);
    }
  };

  const handleCancel = () => {
    if (profile) {
      setEditedProfile({
        ...profile,
        subjects: profile.subjects,
        interests: profile.interests,
        topics: profile.topics,
        availability: profile.availability,
        priceRange: profile.priceRange || { min: 0, max: 0 },
        hourlyRate: profile.hourlyRate,
      } as Profile);
    }
    setIsEditing(false);
  };

  const handleTimeSlotChange = (index: number, field: keyof TimeSlot, value: string | number) => {
    if (editedProfile) {
      const newTimeSlots = [...editedProfile.availability];
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
        availability: [...editedProfile.availability, newTimeSlot],
      } as Profile);
    }
  };

  const removeTimeSlot = (index: number) => {
    if (editedProfile) {
      const newTimeSlots = [...editedProfile.availability];
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
      
      <Container maxWidth="md" sx={{ py: 4, ml: '240px' }}>
        <Paper sx={{ p: 3 }}>
          <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
            <Typography variant="h4">
              {isStudent ? 'Student Profile' : 'Tutor Profile'}
            </Typography>
            {!isEditing && (
              <Button variant="contained" onClick={handleEdit}>
                Edit Profile
              </Button>
            )}
          </Box>

          {/* Read-only Information */}
          <Box sx={{ mb: 4 }}>
            <Typography variant="h6" gutterBottom>Personal Information</Typography>
            <Box sx={{ display: 'flex', flexDirection: { xs: 'column', sm: 'row' }, gap: 3 }}>
              <TextField
                fullWidth
                label="Email"
                value={profile.email}
                disabled
              />
              <TextField
                fullWidth
                label="Full Name"
                value={profile.fullName}
                disabled
              />
            </Box>
          </Box>

          {/* Editable Information */}
          <Box sx={{ mb: 4 }}>
            <Typography variant="h6" gutterBottom>Profile Information</Typography>
            <Grid container spacing={3}>
              <Grid>
                <TextField
                  fullWidth
                  multiline
                  rows={4}
                  label="Bio"
                  value={isEditing ? editedProfile.bio : profile.bio}
                  onChange={(e) => isEditing && setEditedProfile({ ...editedProfile, bio: e.target.value } as Profile)}
                  disabled={!isEditing}
                />
              </Grid>
              <Grid>
                <TextField
                  fullWidth
                  multiline
                  rows={4}
                  label="Education"
                  value={isEditing ? editedProfile.education : profile.education}
                  onChange={(e) => isEditing && setEditedProfile({ ...editedProfile, education: e.target.value } as Profile)}
                  disabled={!isEditing}
                />
              </Grid>
              <Grid>
                <TextField
                  fullWidth
                  multiline
                  rows={4}
                  label="Experience"
                  value={isEditing ? editedProfile.experience : profile.experience}
                  onChange={(e) => isEditing && setEditedProfile({ ...editedProfile, experience: e.target.value } as Profile)}
                  disabled={!isEditing}
                />
              </Grid>
            </Grid>
          </Box>

          {/* Subjects and Interests */}
          <Box sx={{ mb: 4 }}>
            <Typography variant="h6" gutterBottom>
              {isStudent ? 'Interested Subjects' : 'Teaching Subjects'}
            </Typography>
            <TextField
              fullWidth
              multiline
              rows={2}
              label={isStudent ? 'Interested Subjects' : 'Teaching Subjects'}
              value={isEditing ? editedProfile.subjects?.map(s => s.name).join(', ') : profile.subjects?.map(s => s.name).join(', ')}
              onChange={(e) => {
                if (isEditing) {
                  const subjectNames = e.target.value.split(',').map(s => s.trim());
                  const subjects = subjectNames.map(name => ({ name }));
                  setEditedProfile({ ...editedProfile, subjects } as Profile);
                }
              }}
              disabled={!isEditing}
              placeholder={isStudent ? "Enter subjects you're interested in learning" : "Enter subjects you can teach"}
              sx={{ mb: 2 }}
            />
            <Typography variant="body2" color="text.secondary">
              {isStudent 
                ? "Enter subjects you're interested in learning, separated by commas" 
                : "Enter subjects you can teach, separated by commas"}
            </Typography>
          </Box>

          {/* Topics */}
          <Box sx={{ mb: 4 }}>
            <Typography variant="h6" gutterBottom>Topics</Typography>
            {/* <Stack direction="row" spacing={1} flexWrap="wrap" useFlexGap>
              {isEditing ? (
                editedProfile.topics.map((topic, index) => (
                  <Chip
                    key={index}
                    label={topic}
                    onDelete={() => {
                      const newTopics = [...editedProfile.topics];
                      newTopics.splice(index, 1);
                      setEditedProfile({ ...editedProfile, topics: newTopics } as Profile);
                    }}
                  />
                ))
              ) : (
                profile.topics.map((topic, index) => (
                  <Chip key={index} label={topic} />
                ))
              )}
            </Stack> */}
          </Box>

          {/* Price Range or Hourly Rate */}
          {isStudent ? (
            <Box sx={{ mb: 4 }}>
              <Typography variant="h6" gutterBottom>Price Range</Typography>
              <Grid container spacing={2}>
                <Grid>
                  <TextField
                    fullWidth
                    label="Minimum Price"
                    type="number"
                    value={isEditing ? editedProfile.priceRange?.min : profile.priceRange?.min}
                    onChange={(e) => isEditing && setEditedProfile({
                      ...editedProfile,
                      priceRange: { ...editedProfile.priceRange, min: Number(e.target.value) }
                    } as Profile)}
                    disabled={!isEditing}
                  />
                </Grid>
                <Grid>
                  <TextField
                    fullWidth
                    label="Maximum Price"
                    type="number"
                    value={isEditing ? editedProfile.priceRange?.max : profile.priceRange?.max}
                    onChange={(e) => isEditing && setEditedProfile({
                      ...editedProfile,
                      priceRange: { ...editedProfile.priceRange, max: Number(e.target.value) }
                    } as Profile)}
                    disabled={!isEditing}
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
                value={isEditing ? editedProfile.hourlyRate : profile.hourlyRate}
                onChange={(e) => isEditing && setEditedProfile({
                  ...editedProfile,
                  hourlyRate: Number(e.target.value)
                } as Profile)}
                disabled={!isEditing}
              />
            </Box>
          )}

          {/* Availability */}
          <Box sx={{ mb: 4 }}>
            <Typography variant="h6" gutterBottom>Availability</Typography>
            {/* <Stack spacing={2}>
              {isEditing ? (
                editedProfile.availability.map((slot, index) => (
                  <Box key={index} sx={{ display: 'flex', gap: 2, alignItems: 'center' }}>
                    <FormControl sx={{ minWidth: 120 }}>
                      <InputLabel>Day</InputLabel>
                      <Select
                        value={slot.dayOfWeek}
                        label="Day"
                        onChange={(e) => handleTimeSlotChange(index, 'dayOfWeek', Number(e.target.value))}
                      >
                        {DAYS_OF_WEEK.map((day, i) => (
                          <MenuItem key={i} value={i}>{day}</MenuItem>
                        ))}
                      </Select>
                    </FormControl>
                    <TextField
                      type="time"
                      label="Start Time"
                      value={slot.startTime}
                      onChange={(e) => handleTimeSlotChange(index, 'startTime', e.target.value)}
                      InputLabelProps={{ shrink: true }}
                    />
                    <TextField
                      type="time"
                      label="End Time"
                      value={slot.endTime}
                      onChange={(e) => handleTimeSlotChange(index, 'endTime', e.target.value)}
                      InputLabelProps={{ shrink: true }}
                    />
                    <IconButton onClick={() => removeTimeSlot(index)} color="error">
                      <DeleteIcon />
                    </IconButton>
                  </Box>
                ))
              ) 
              // : (
              //   profile.availability.map((slot, index) => (
              //     <Box key={index} sx={{ display: 'flex', gap: 2, alignItems: 'center' }}>
              //       <Typography>{DAYS_OF_WEEK[slot.dayOfWeek]}</Typography>
              //       <Typography>{slot.startTime} - {slot.endTime}</Typography>
              //     </Box>
              //   ))
              // )
              }
              {isEditing && (
                <Button
                  startIcon={<AddIcon />}
                  onClick={addTimeSlot}
                  variant="outlined"
                >
                  Add Time Slot
                </Button>
              )}
            </Stack> */}
          </Box>

          {/* Edit Actions */}
          {isEditing && (
            <Box sx={{ display: 'flex', gap: 2, justifyContent: 'flex-end' }}>
              <Button variant="outlined" onClick={handleCancel}>
                Cancel
              </Button>
              <Button variant="contained" onClick={handleSave}>
                Save Changes
              </Button>
            </Box>
          )}
        </Paper>
      </Container>
    </Layout>
  );
};

export default ProfilePage;
