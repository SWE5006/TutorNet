import React, { useState, useEffect } from "react";
import {
  Container,
  Typography,
  Box,
  TextField,
  MenuItem,
  Select,
  FormControl,
  InputLabel,
  Card,
  CardContent,
  Grid,
  CircularProgress,
  Alert,
  SelectChangeEvent,
  Avatar,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  IconButton,
} from "@mui/material";
import Layout from "../components/Layout";
import Sidebar from "../components/Layout/sidebar";
import { submitInterest, TimeSlotDto } from "../services/interest.service";
import DeleteIcon from '@mui/icons-material/Delete';
import { useSelector } from "react-redux";
import { selectAuthSlice } from "../state/auth/slice";


interface Tutor {
  id: string;
  name: string;
  subject: string;
  description: string;
  location: string;
  rating: number;
}



const TUTORS: Tutor[] = [
  {
    id: "4ba7193a-dc0f-40e9-85fc-0f7e57fbee13",
    name: "Alice Wonderland",
    subject: "Mathematics",
    description:
      "Experienced in algebra, calculus, and geometry. Passionate about making math fun!",
    location: "Singapore",
    rating: 4.8,
  },
  {
    id: "b2e1c7a2-1e2b-4c3d-9f8a-2e4b5c6d7e8f",
    name: "Bob The Builder",
    subject: "Physics",
    description:
      "Specializes in classical mechanics and electromagnetism. Prepares students for competitive exams.",
    location: "Jurong East",
    rating: 4.5,
  },
  {
    id: "c3d2e1f4-5b6a-7c8d-9e0f-1a2b3c4d5e6f",
    name: "Charlie Chaplin",
    subject: "English",
    description:
      "Focuses on essay writing, grammar, and literature analysis. Helps improve verbal communication.",
    location: "Tampines",
    rating: 4.9,
  },
  {
    id: "d4e3f2a1-6b7c-8d9e-0f1a-2b3c4d5e6f7a",
    name: "Diana Prince",
    subject: "Chemistry",
    description:
      "Expert in organic and inorganic chemistry. Provides clear explanations and problem-solving strategies.",
    location: "Ang Mo Kio",
    rating: 4.7,
  },
  {
    id: "e5f4a3b2-7c8d-9e0f-1a2b-3c4d5e6f7a8b",
    name: "Eve Adams",
    subject: "Mathematics",
    description:
      "Certified tutor for primary and secondary school mathematics. Patient and encouraging.",
    location: "Woodlands",
    rating: 4.6,
  },
  {
    id: "f6a5b4c3-8d9e-0f1a-2b3c-4d5e6f7a8b9c",
    name: "Frank Ocean",
    subject: "Biology",
    description:
      "Covers cell biology, genetics, and ecology. Makes complex topics easy to understand.",
    location: "Novena",
    rating: 4.4,
  },
  {
    id: "a7b8c9d0-1e2f-3a4b-5c6d-7e8f9a0b1c2d",
    name: "Grace Hopper",
    subject: "Computer Science",
    description:
      "Programming fundamentals (Python, Java), data structures, and algorithms.",
    location: "Bishan",
    rating: 5.0,
  },
];



function TutorListPage() {
  const [searchTerm, setSearchTerm] = useState<string>("");
  const [selectedSubject, setSelectedSubject] =
    useState<string>("All Subjects");
  const [filteredTutors, setFilteredTutors] = useState<Tutor[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [showDialog, setShowDialog] = useState(false);
  const [selectedSubjectId, setSelectedSubjectId] = useState<string | null>(null);
  const [timeSlots, setTimeSlots] = useState<TimeSlotDto[]>([{ startTime: '', endTime: '', dayOfWeek: 1 }]);
  const { userInfo, isLoggedIn } = useSelector((state) => selectAuthSlice(state));
  const userEmail = userInfo?.email_address || userInfo?.userEmail || "";

  const ALL_SUBJECTS: string[] = [
  "All Subjects"
  ];

  useEffect(() => {
    const fetchTutors = async () => {
      try {
        setLoading(true);
        setError(null);
        await new Promise((resolve) => setTimeout(resolve, 500));
        setFilteredTutors(TUTORS);
      } catch (err: any) {
        setError("Failed to load tutors. Please try again.");
        console.error("Error fetching tutors:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchTutors();
  }, []);

  // Filter tutors whenever search term or subject changes
  useEffect(() => {
    let currentTutors: Tutor[] = TUTORS; // Explicitly type currentTutors

    if (selectedSubject !== "All Subjects") {
      currentTutors = currentTutors.filter(
        (tutor) => tutor.subject === selectedSubject
      );
    }

    if (searchTerm) {
      const lowerCaseSearchTerm = searchTerm.toLowerCase();
      currentTutors = currentTutors.filter(
        (tutor) =>
          tutor.name.toLowerCase().includes(lowerCaseSearchTerm) ||
          tutor.subject.toLowerCase().includes(lowerCaseSearchTerm) ||
          tutor.description.toLowerCase().includes(lowerCaseSearchTerm) ||
          tutor.location.toLowerCase().includes(lowerCaseSearchTerm)
      );
    }

    setFilteredTutors(currentTutors);
  }, [searchTerm, selectedSubject]);

  // Event handler for TextField change
  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value);
  };

  // Event handler for Select (dropdown) change
  const handleSubjectChange = (event: SelectChangeEvent<string>) => {
    // Use SelectChangeEvent
    setSelectedSubject(event.target.value);
  };

  const handleInterest = (subjectId: string) => {
    setSelectedSubjectId(subjectId);
    setShowDialog(true);
  };

  const handleDialogChange = (idx: number, field: keyof TimeSlotDto, value: string | number) => {
    const newSlots = [...timeSlots];
    newSlots[idx] = { ...newSlots[idx], [field]: value };
    setTimeSlots(newSlots);
  };

  // Add debug logging
  useEffect(() => {
    console.log('Auth State:', { userInfo, isLoggedIn, userEmail });
  }, [userInfo, isLoggedIn, userEmail]);

  const handleSubmit = async () => {
    if (!isLoggedIn) {
      alert("Please login first.");
      return;
    }
    if (!userEmail) {
      console.error('User email is missing:', { userInfo, isLoggedIn, userEmail });
      alert("User email is missing. Please try logging out and logging back in.");
      return;
    }
    if (!selectedSubjectId) {
      alert("Please select a subject first.");
      return;
    }
    try {
      await submitInterest({
        userId: userEmail,
        subjectId: selectedSubjectId,
        availableTimeSlots: timeSlots
      });
      setShowDialog(false);
      setTimeSlots([{ startTime: '', endTime: '', dayOfWeek: 1 }]);
      setSelectedSubjectId(null);
      alert("Interest submitted successfully!");
    } catch (err) {
      alert("Failed to submit interest. Please try again.");
      console.error(err);
    }
  };

  return (
    
    <Layout isLoading={false}>
       <Sidebar></Sidebar>
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
     
        {/* Filter Section */}
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
            onChange={handleSearchChange} // Use typed handler
            sx={{ width: { xs: "100%", sm: "auto" }, flexGrow: 1 }}
          />
          <FormControl sx={{ width: { xs: "100%", sm: 200 } }}>
            <InputLabel id="subject-select-label">Subject</InputLabel>
            <Select
              labelId="subject-select-label"
              id="subject-select"
              value={selectedSubject}
              label="Subject"
              onChange={handleSubjectChange} // Use typed handler
            >
              {ALL_SUBJECTS.map((subject) => (
                <MenuItem key={subject} value={subject}>
                  {subject}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>

        {/* Tutor List Section */}
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
        ) : error ? (
          <Alert severity="error">{error}</Alert>
        ) : filteredTutors.length === 0 ? (
          <Alert severity="info">No tutors found matching your criteria.</Alert>
        ) : (
          <Grid container spacing={3} justifyContent="center">
            {filteredTutors.map((tutor, idx) => (
              <Grid item xs={12} sm={6} md={6} key={tutor.id} sx={{ display: 'flex', justifyContent: 'center' }}>
                <Card sx={{ width: 520, height: 380, display: 'flex', flexDirection: 'column', boxShadow: 6, m: 1 }}>
                  {/* 顶部照片/头像占位 */}
                  <Box sx={{ width: '100%', height: 120, bgcolor: 'grey.300', display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: 32, color: '#888' }}>
                    IMG
                  </Box>
                  <CardContent sx={{ flexGrow: 1, display: 'flex', flexDirection: 'column', justifyContent: 'space-between', p: 2 }}>
                    <Box>
                      <Typography variant="h6" component="div" gutterBottom>
                        {tutor.name}
                      </Typography>
                      <Typography variant="subtitle1" color="primary" sx={{ mb: 1 }}>
                        {tutor.subject}
                      </Typography>
                      <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
                        {tutor.description}
                      </Typography>
                      <Typography variant="body2" color="text.secondary">
                        <Box component="span" fontWeight="medium">Location:</Box> {tutor.location}
                      </Typography>
                    </Box>
                    <Box sx={{ mt: 2 }}>
                      <Button variant="contained" color="primary" fullWidth onClick={() => handleInterest(tutor.id)}>
                        Interested
                      </Button>
                    </Box>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        )}
        {/* Interested Dialog */}
        <Dialog open={showDialog} onClose={() => setShowDialog(false)} maxWidth="sm" fullWidth>
          <DialogTitle>Express Your Interest</DialogTitle>
          <DialogContent sx={{ minWidth: 400, minHeight: 220 }}>
            <Typography sx={{ mb: 2 }}>
              Please select your available time slots for this subject. You can add multiple slots.
            </Typography>
            {timeSlots.map((slot, idx) => (
              <Box key={idx} sx={{ display: 'flex', gap: 2, alignItems: 'center', mb: 1 }}>
                <FormControl sx={{ minWidth: 120 }}>
                  <InputLabel>Day</InputLabel>
                  <Select
                    value={slot.dayOfWeek}
                    label="Day"
                    onChange={e => handleDialogChange(idx, 'dayOfWeek', Number(e.target.value))}
                  >
                    {["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"].map((day, i) => (
                      <MenuItem key={i} value={i}>{day}</MenuItem>
                    ))}
                  </Select>
                </FormControl>
                <TextField
                  type="time"
                  label="Start Time"
                  value={slot.startTime}
                  onChange={e => handleDialogChange(idx, 'startTime', e.target.value)}
                  InputLabelProps={{ shrink: true }}
                />
                <TextField
                  type="time"
                  label="End Time"
                  value={slot.endTime}
                  onChange={e => handleDialogChange(idx, 'endTime', e.target.value)}
                  InputLabelProps={{ shrink: true }}
                />
                {timeSlots.length > 1 && (
                  <IconButton onClick={() => setTimeSlots(timeSlots.filter((_, i) => i !== idx))} color="error">
                    <DeleteIcon />
                  </IconButton>
                )}
              </Box>
            ))}
            <Button onClick={() => setTimeSlots([...timeSlots, { startTime: '', endTime: '', dayOfWeek: 1 }])} sx={{ mt: 1 }}>
              Add Time Slot
            </Button>
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setShowDialog(false)}>Cancel</Button>
            <Button variant="contained" onClick={handleSubmit}>Submit</Button>
          </DialogActions>
        </Dialog>
      </Container>
    </Layout>
  );
}

export default TutorListPage;
