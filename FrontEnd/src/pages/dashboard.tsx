import React, { useState, useEffect } from "react";
import {
  Container,
  Typography,
  Box,
  TextField,
  Card,
  CardContent,
  Grid,
  CircularProgress,
  Alert,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  List,
  ListItem,
  ListItemText,
  Checkbox,
  ListItemIcon,
} from "@mui/material";
import Layout from "../components/Layout";
import Sidebar from "../components/Layout/sidebar";
import { submitInterest } from "../services/interest.service";
import { useSelector } from "react-redux";
import { selectAuthSlice } from "../state/auth/slice";
import { useGetTutorsQuery } from "../services/tutor.service";

interface Tutor {
  id: string;           // UUID from backend
  username: string;     // Changed from name to username
  bio: string;         
  education: string;    // Added new field
  experience: string;   // Added new field
  hourlyRate: number;   // Added new field
  subjects: string;     // Changed from subject to subjects
}

const SAMPLE_TIME_SLOTS = [
  { id: 1, day: 'Monday', time: '9:00 AM - 11:00 AM' },
  { id: 2, day: 'Monday', time: '2:00 PM - 4:00 PM' },
  { id: 3, day: 'Wednesday', time: '10:00 AM - 12:00 PM' },
  { id: 4, day: 'Thursday', time: '3:00 PM - 5:00 PM' },
  { id: 5, day: 'Friday', time: '1:00 PM - 3:00 PM' },
];

function TutorListPage() {
  const [searchTerm, setSearchTerm] = useState<string>("");
  const [filteredTutors, setFilteredTutors] = useState<Tutor[]>([]);
  const [showDialog, setShowDialog] = useState(false);
  const [selectedSubjectId, setSelectedSubjectId] = useState<string | null>(null);
  const [selectedSubject, setSelectedSubject] = useState<string>("All");
  const [selectedTimeSlots, setSelectedTimeSlots] = useState<number[]>([]);
  const { userInfo, isLoggedIn } = useSelector((state) => selectAuthSlice(state));
  const userEmail = userInfo?.email_address || userInfo?.userEmail || "";

  const { data: allTutors = [], isLoading: loading, error } = useGetTutorsQuery();

  const SUBJECTS = [
    "All",
    "Mathematics",
    "Physics",
    "Chemistry",
    "Biology",
    "Computer Science",
    "English"
  ];

  useEffect(() => {
    let currentTutors: Tutor[] = (allTutors as any[]).map((tutor: any) => ({
      id: tutor.id,
      username: tutor.username,
      bio: tutor.bio ?? "",
      education: tutor.education ?? "",
      experience: tutor.experience ?? "",
      hourlyRate: tutor.hourlyRate ?? 0,
      subjects: tutor.subjects ?? ""
    }));

    if (searchTerm || selectedSubject !== "All") {
      const lowerCaseSearchTerm = searchTerm.toLowerCase();
      currentTutors = currentTutors.filter((tutor) => {
        const matchesSearch = 
          tutor.username.toLowerCase().includes(lowerCaseSearchTerm) ||
          tutor.subjects.toLowerCase().includes(lowerCaseSearchTerm) ||
          tutor.bio.toLowerCase().includes(lowerCaseSearchTerm) ||
          tutor.education.toLowerCase().includes(lowerCaseSearchTerm) ||
          tutor.experience.toLowerCase().includes(lowerCaseSearchTerm);
        
        const matchesSubject = selectedSubject === "All" || 
          tutor.subjects.toLowerCase().includes(selectedSubject.toLowerCase());

        return matchesSearch && matchesSubject;
      });
    }

    setFilteredTutors(currentTutors);
  }, [searchTerm, selectedSubject, allTutors]);

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value);
  };

  const handleInterest = (subjectId: string) => {
    if (!isLoggedIn) {
      alert("Please login first.");
      return;
    }
    if (!userEmail) {
      console.error('User email is missing:', { userInfo, isLoggedIn, userEmail });
      alert("User email is missing. Please try logging out and logging back in.");
      return;
    }
    setSelectedSubjectId(subjectId);
    setShowDialog(true);
  };

  const handleTimeSlotToggle = (slotId: number) => {
    setSelectedTimeSlots(prev => {
      if (prev.includes(slotId)) {
        return prev.filter(id => id !== slotId);
      } else {
        return [...prev, slotId];
      }
    });
  };

  const handleSubmit = async () => {
    try {
      if (selectedTimeSlots.length === 0) {
        alert("Please select at least one time slot.");
        return;
      }
      
      // Map day names to numbers: Sunday=0, Monday=1, ..., Saturday=6
      const dayNameToNumber: { [key: string]: number } = {
        Sunday: 0,
        Monday: 1,
        Tuesday: 2,
        Wednesday: 3,
        Thursday: 4,
        Friday: 5,
        Saturday: 6,
      };

      const selectedSlots = SAMPLE_TIME_SLOTS
        .filter(slot => selectedTimeSlots.includes(slot.id))
        .map(slot => {
          // slot.time is like "9:00 AM - 11:00 AM"
          const [startTime, endTime] = slot.time.split(" - ");
          return {
            dayOfWeek: dayNameToNumber[slot.day] ?? 0,
            startTime,
            endTime
          };
        });

      await submitInterest({
        userId: userEmail,
        subjectId: selectedSubjectId!,
        availableTimeSlots: selectedSlots
      });
      
      setShowDialog(false);
      setSelectedSubjectId(null);
      setSelectedTimeSlots([]); // Reset selected slots
      alert("Interest submitted successfully!");
    } catch (err) {
      alert("Failed to submit interest. Please try again.");
      console.error(err);
    }
  };

  const handleSubjectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedSubject(event.target.value);
  };

  return (
    <Layout isLoading={false}>
      <Sidebar />
      <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Typography
          variant="h4"
          component="h1"
          gutterBottom
          align="center"
          sx={{ mb: 4 }}
        >
          Recommended Tutors
        </Typography>

        <Box
          sx={{
            mb: 4,
            p: 3,
            bgcolor: "background.paper",
            borderRadius: 2,
            boxShadow: 3,
            display: "flex",
            flexDirection: { xs: "column", sm: "row" },
            gap: 2,
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <TextField
            label="Search Tutors"
            variant="outlined"
            value={searchTerm}
            onChange={handleSearchChange}
            sx={{ width: { xs: "100%", sm: "auto" }, flexGrow: 1 }}
          />
          <FormControl sx={{ minWidth: 200 }}>
            <InputLabel>Filter by Subject</InputLabel>
            <Select
              value={selectedSubject}
              label="Filter by Subject"
              onChange={(e) => handleSubjectChange(e as any)}
            >
              {SUBJECTS.map((subject) => (
                <MenuItem key={subject} value={subject}>
                  {subject}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>

        {loading ? (
          <Box
            display="flex"
            justifyContent="center"
            alignItems="center"
            height="200px"
          >
            <CircularProgress />
            <Typography variant="h6" sx={{ ml: 2 }}>
              Loading tutors...
            </Typography>
          </Box>
        ) : filteredTutors.length === 0 ? (
          <Alert severity="info">No tutors found matching your criteria.</Alert>
        ) : (
          <Grid container spacing={3} justifyContent="center">
            {filteredTutors.map((tutor) => (
              <Grid key={tutor.id}>
                <Card sx={{ inlineSize: 520, blockSize: 380, display: 'flex', flexDirection: 'column', boxShadow: 6, m: 1 }}>
                  <Box sx={{ display: 'flex', flexDirection: 'row', alignItems: 'flex-start' }}>
                    <Box
                      sx={{
                        inlineSize: 310,
                        blockSize: 200,
                        bgcolor: 'grey.300',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        fontSize: 32,
                        color: '#888',
                        borderRadius: 2,
                        overflow: 'hidden',
                        mr: 2,
                        float: 'left',
                      }}
                    >
                      <img
                        src="/images/profileicon.png"
                        alt={tutor.username}
                        style={{ inlineSize: '100%', blockSize: '100%', objectFit: 'cover' }}
                      />
                    </Box>
                    <CardContent sx={{ flexGrow: 1, display: 'flex', flexDirection: 'column', justifyContent: 'space-between', p: 2 }}>
                      <Box>
                        <Typography variant="h6" component="div" gutterBottom>
                          {tutor.username}
                        </Typography>
                        <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
                          {tutor.bio}
                        </Typography>
                        <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
                          <Box component="span" fontWeight="medium">Education:</Box> {tutor.education}
                        </Typography>
                        <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
                          <Box component="span" fontWeight="medium">Experience:</Box> {tutor.experience}
                        </Typography>
                        <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
                          <Box component="span" fontWeight="medium">Rate:</Box> ${tutor.hourlyRate}/hour
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                          <Box component="span" fontWeight="medium">Subjects:</Box> {tutor.subjects}
                        </Typography>
                      </Box>
                      <Box sx={{ mt: 2 }}>
                        <Button variant="contained" color="primary" fullWidth onClick={() => handleInterest(tutor.id)}>
                          Interested
                        </Button>
                      </Box>
                    </CardContent>
                  </Box>
                </Card>
              </Grid>
            ))}
          </Grid>
        )}

        <Dialog open={showDialog} onClose={() => setShowDialog(false)} maxWidth="sm" fullWidth>
          <DialogTitle>Express Your Interest</DialogTitle>
          <DialogContent sx={{ width: 400, minHeight: 300 }}>
            <Typography variant="h6" sx={{ mb: 2 }}>
              Available Time Slots
            </Typography>
            <Box sx={{ mb: 2 }}>
              <List sx={{ 
                width: '100%', 
                bgcolor: 'background.paper',
                border: '1px solid',
                borderColor: 'grey.300',
                borderRadius: 1,
                maxHeight: 200,
                overflow: 'auto'
              }}>
                {SAMPLE_TIME_SLOTS.map((slot) => (
                  <ListItem
                    key={slot.id}
                    sx={{
                      borderBottom: '1px solid',
                      borderColor: 'grey.200',
                      '&:last-child': {
                        borderBottom: 'none'
                      }
                    }}
                  >
                    <ListItemIcon>
                      <Checkbox
                        edge="start"
                        checked={selectedTimeSlots.includes(slot.id)}
                        onChange={() => handleTimeSlotToggle(slot.id)}
                        tabIndex={-1}
                      />
                    </ListItemIcon>
                    <ListItemText
                      primary={slot.day}
                      secondary={slot.time}
                      sx={{
                        '& .MuiListItemText-primary': {
                          fontWeight: 'medium'
                        }
                      }}
                    />
                  </ListItem>
                ))}
              </List>
            </Box>
            <Typography sx={{ mb: 2 }}>
              Would you like to express interest in this tutor?
            </Typography>
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setShowDialog(false)}>Cancel</Button>
            <Button variant="contained" onClick={handleSubmit}>Book Tutor</Button>
          </DialogActions>
        </Dialog>
      </Container>
    </Layout>
  );
}

export default TutorListPage;
