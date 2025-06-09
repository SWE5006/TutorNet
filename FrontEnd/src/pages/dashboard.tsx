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
  IconButton,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import Layout from "../components/Layout";
import Sidebar from "../components/Layout/sidebar";
import { submitInterest, TimeSlotDto } from "../services/interest.service";
import DeleteIcon from '@mui/icons-material/Delete';
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

function TutorListPage() {
  const [searchTerm, setSearchTerm] = useState<string>("");
  const [filteredTutors, setFilteredTutors] = useState<Tutor[]>([]);
  const [showDialog, setShowDialog] = useState(false);
  const [selectedSubjectId, setSelectedSubjectId] = useState<string | null>(null);
  const [timeSlots, setTimeSlots] = useState<TimeSlotDto[]>([{ startTime: '', endTime: '', dayOfWeek: 1 }]);
  const { userInfo, isLoggedIn } = useSelector((state) => selectAuthSlice(state));
  const userEmail = userInfo?.email_address || userInfo?.userEmail || "";

  const { data: allTutors = [], isLoading: loading, error } = useGetTutorsQuery();

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

    if (searchTerm) {
      const lowerCaseSearchTerm = searchTerm.toLowerCase();
      currentTutors = currentTutors.filter(
        (tutor) =>
          tutor.username.toLowerCase().includes(lowerCaseSearchTerm) ||
          tutor.subjects.toLowerCase().includes(lowerCaseSearchTerm) ||
          tutor.bio.toLowerCase().includes(lowerCaseSearchTerm) ||
          tutor.education.toLowerCase().includes(lowerCaseSearchTerm) ||
          tutor.experience.toLowerCase().includes(lowerCaseSearchTerm)
      );
    }

    setFilteredTutors(currentTutors);
  }, [searchTerm, allTutors]);

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value);
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
          <DialogContent sx={{ width: 400, minHeight: 220 }}>
            <Typography sx={{ mb: 2 }}>
              Please select your available time slots for this subject. You can add multiple slots.
            </Typography>
            {timeSlots.map((slot, idx) => (
              <Box key={idx} sx={{ display: 'flex', gap: 2, alignItems: 'center', mb: 1 }}>
                <FormControl sx={{ minInlineSize: 120 }}>
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
