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
import { submitInterest } from "../services/interest.service";
import { useSelector } from "react-redux";
import { selectAuthSlice } from "../state/auth/slice";
import { useGetTutorsQuery, useGetTutorTimeSlotsQuery } from "../services/tutor.service";

const DAYS_OF_WEEK = [
  "Sunday",
  "Monday",
  "Tuesday",
  "Wednesday",
  "Thursday",
  "Friday",
  "Saturday"
];

interface Tutor {
  id: string;           // UUID from backend
  username: string;     // Changed from name to username
  bio: string;         
  education: string;    // Added new field
  experience: string;   // Added new field
  hourlyRate: number;   // Added new field
  subjects: string;     // Changed from subject to subjects
  email: string;        // Added new field
}

function TutorListPage() {
  const [searchTerm, setSearchTerm] = useState<string>("");
  const [filteredTutors, setFilteredTutors] = useState<Tutor[]>([]);
  const [showDialog, setShowDialog] = useState(false);
  const [selectedSubjectId, setSelectedSubjectId] = useState<string | null>(null);
  const [selectedSubject, setSelectedSubject] = useState<string>("All");
  const [selectedTimeSlots, setSelectedTimeSlots] = useState<number[]>([]);
  const [selectedTutorEmail, setSelectedTutorEmail] = useState<string | null>(null);
  const { userInfo, isLoggedIn } = useSelector((state) => selectAuthSlice(state));
  const userEmail = userInfo?.email_address || userInfo?.userEmail || "";

  const { data: allTutors = [], isLoading: loading, error } = useGetTutorsQuery();
  const { data: timeSlots = [], isLoading: timeSlotsLoading } = useGetTutorTimeSlotsQuery(
    selectedTutorEmail ?? '',
    {
      skip: !selectedTutorEmail,
    }
  );

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
      subjects: tutor.subjects ?? "",
      email: tutor.email || tutor.emailAddress || ""
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

  const handleInterest = (tutorEmail: string) => {
    if (!isLoggedIn) {
      alert("Please login first.");
      return;
    }
    if (!userEmail) {
      console.error('User email is missing:', { userInfo, isLoggedIn, userEmail });
      alert("User email is missing. Please try logging out and logging back in.");
      return;
    }
    setSelectedTutorEmail(tutorEmail);
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

      const selectedSlots = timeSlots
        .filter(slot => selectedTimeSlots.includes(Number(slot.id)))
        .map(slot => ({
          dayOfWeek: typeof slot.dayOfWeek === "number" ? slot.dayOfWeek : parseInt(slot.dayOfWeek, 10),
          startTime: slot.startTime,
          endTime: slot.endTime
        }));

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

  console.log('selectedTutorEmail', selectedTutorEmail, 'timeSlots', timeSlots);

  return (
    <Layout isLoading={false}>
     
      <Container 
        sx={{ 
          pl: 2, // Reduce left padding
          pr: 2, // Keep consistent right padding
          maxWidth: 'none' // Remove default maxWidth constraint
        }}
      >
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
          <Grid container>
            {filteredTutors.map((tutor) => (
              <Grid key={tutor.id}>
                <Card sx={{ inlineSize: 520, blockSize: 380, display: 'flex', flexDirection: 'column', boxShadow: 6, m: 1 }}>
                  <Box sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center', minHeight: 120 }}>
                    <Box
                      sx={{
                        width: 120,
                        height: 120,
                        bgcolor: 'grey.300',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        fontSize: 32,
                        color: '#888',
                        borderRadius: 2,
                        overflow: 'hidden',
                        mr: 2,
                        flexShrink: 0,
                      }}
                    >
                      <img
                        src="/images/profileicon.png"
                        alt={tutor.username}
                        style={{ width: '100px', height: '100px', objectFit: 'cover', borderRadius: '50%' }}
                      />
                    </Box>
                    <CardContent sx={{ flexGrow: 1, minHeight: 120, display: 'flex', flexDirection: 'column', justifyContent: 'center', p: 2 }}>
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
                        <Button variant="contained" color="primary" fullWidth onClick={() => handleInterest(tutor.email)}>
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
                {timeSlots.map((slot) => (
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
                        checked={selectedTimeSlots.includes(Number(slot.id))}
                        onChange={() => handleTimeSlotToggle(Number(slot.id))}
                        tabIndex={-1}
                      />
                    </ListItemIcon>
                    <ListItemText
                      primary={DAYS_OF_WEEK[parseInt(String(slot.dayOfWeek))]}
                      secondary={`${slot.startTime} - ${slot.endTime}`}
                      sx={{
                        '& .MuiListItemText-primary': {
                          fontWeight: 'medium'
                        }
                      }}
                    />
                  </ListItem>
                ))}
              </List>
              <pre style={{fontSize:12, color:'#888', marginTop:8}}>{JSON.stringify(timeSlots, null, 2)}</pre>
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
