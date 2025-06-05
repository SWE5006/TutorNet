import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import {
  Container,
  Typography,
  Box,
  Card,
  CardContent,
  Grid,
  Button,
  Chip,
  Stack,
  Divider,
  Alert,
  CircularProgress,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from "@mui/material";
import { selectAuthSlice } from "../state/auth/slice";
import Layout from "../components/Layout";
import Sidebar from "../components/Layout/sidebar";
import { createBooking } from "../services/booking.service";

interface Match {
  id: string;
  student: {
    id: string;
    name: string;
    email: string;
    subjects: string[];
    availability: TimeSlot[];
  };
  tutor: {
    id: string;
    name: string;
    email: string;
    subjects: string[];
    availability: TimeSlot[];
  };
  commonSubjects: string[];
  commonTimeSlots: TimeSlot[];
}

interface TimeSlot {
  dayOfWeek: number;
  startTime: string;
  endTime: string;
}

const DAYS_OF_WEEK = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

export default function Booking() {
  const { isLoggedIn, userInfo } = useSelector((state) => selectAuthSlice(state));
  const [matches, setMatches] = useState<Match[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string>("");
  const [selectedMatch, setSelectedMatch] = useState<Match | null>(null);
  const [showConfirmDialog, setShowConfirmDialog] = useState(false);

  // Mock data for demonstration
  useEffect(() => {
    const mockMatches: Match[] = [
      {
        id: "1",
        student: {
          id: "student1",
          name: "John Doe",
          email: "john@example.com",
          subjects: ["Mathematics", "Physics"],
          availability: [
            { dayOfWeek: 1, startTime: "09:00", endTime: "11:00" },
            { dayOfWeek: 3, startTime: "14:00", endTime: "16:00" }
          ]
        },
        tutor: {
          id: "tutor1",
          name: "Alice Smith",
          email: "alice@example.com",
          subjects: ["Mathematics", "Physics", "Chemistry"],
          availability: [
            { dayOfWeek: 1, startTime: "09:00", endTime: "12:00" },
            { dayOfWeek: 3, startTime: "13:00", endTime: "17:00" }
          ]
        },
        commonSubjects: ["Mathematics", "Physics"],
        commonTimeSlots: [
          { dayOfWeek: 1, startTime: "09:00", endTime: "11:00" },
          { dayOfWeek: 3, startTime: "14:00", endTime: "16:00" }
        ]
      },
      // Add more mock matches as needed
    ];

    setMatches(mockMatches);
    setLoading(false);
  }, []);

  const handleConfirmMatch = async (match: Match) => {
    setSelectedMatch(match);
    setShowConfirmDialog(true);
  };

  const handleConfirmBooking = async () => {
    if (!selectedMatch) return;

    try {
      // Create booking for each common time slot
      for (const slot of selectedMatch.commonTimeSlots) {
        await createBooking({
          studentId: selectedMatch.student.id,
          tutorId: selectedMatch.tutor.id,
          subjectName: selectedMatch.commonSubjects[0], // Use first common subject
          dayOfWeek: slot.dayOfWeek,
          startTime: slot.startTime,
          endTime: slot.endTime
        });
      }

      // Remove the confirmed match from the list
      setMatches(matches.filter(m => m.id !== selectedMatch.id));
      setShowConfirmDialog(false);
      setSelectedMatch(null);
    } catch (err: any) {
      setError(err.message || "Failed to create booking");
    }
  };

  if (loading) {
    return (
      <Layout isLoading={true}>
        <Container maxWidth="lg" sx={{ py: 4 }}>
          <CircularProgress />
        </Container>
      </Layout>
    );
  }

  return (
    <Layout isLoading={false}>
      <Sidebar />
      <Container maxWidth="lg" sx={{ py: 4, ml: '240px' }}>
        <Typography variant="h4" gutterBottom>
          Available Matches
        </Typography>
        
        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}

        <Grid container spacing={3}>
          {matches.map((match) => (
            <Grid item xs={12} key={match.id}>
              <Card>
                <CardContent>
                  <Grid container spacing={2}>
                    <Grid item xs={12} md={5}>
                      <Typography variant="h6" gutterBottom>
                        Student
                      </Typography>
                      <Typography>Name: {match.student.name}</Typography>
                      <Typography>Email: {match.student.email}</Typography>
                      <Box sx={{ mt: 1 }}>
                        <Typography variant="subtitle2">Subjects:</Typography>
                        <Stack direction="row" spacing={1} sx={{ mt: 1 }}>
                          {match.student.subjects.map((subject) => (
                            <Chip key={subject} label={subject} size="small" />
                          ))}
                        </Stack>
                      </Box>
                    </Grid>

                    <Grid item xs={12} md={5}>
                      <Typography variant="h6" gutterBottom>
                        Tutor
                      </Typography>
                      <Typography>Name: {match.tutor.name}</Typography>
                      <Typography>Email: {match.tutor.email}</Typography>
                      <Box sx={{ mt: 1 }}>
                        <Typography variant="subtitle2">Subjects:</Typography>
                        <Stack direction="row" spacing={1} sx={{ mt: 1 }}>
                          {match.tutor.subjects.map((subject) => (
                            <Chip key={subject} label={subject} size="small" />
                          ))}
                        </Stack>
                      </Box>
                    </Grid>

                    <Grid item xs={12}>
                      <Divider sx={{ my: 2 }} />
                      <Typography variant="h6" gutterBottom>
                        Common Time Slots
                      </Typography>
                      <Stack direction="row" spacing={1} sx={{ mt: 1 }}>
                        {match.commonTimeSlots.map((slot, index) => (
                          <Chip
                            key={index}
                            label={`${DAYS_OF_WEEK[slot.dayOfWeek]} ${slot.startTime}-${slot.endTime}`}
                            color="primary"
                            variant="outlined"
                          />
                        ))}
                      </Stack>
                    </Grid>

                    <Grid item xs={12}>
                      <Box sx={{ display: 'flex', justifyContent: 'flex-end', mt: 2 }}>
                        <Button
                          variant="contained"
                          color="primary"
                          onClick={() => handleConfirmMatch(match)}
                        >
                          Confirm Match
                        </Button>
                      </Box>
                    </Grid>
                  </Grid>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>

        <Dialog open={showConfirmDialog} onClose={() => setShowConfirmDialog(false)}>
          <DialogTitle>Confirm Match</DialogTitle>
          <DialogContent>
            <Typography>
              Are you sure you want to confirm this match? This will create a booking for all common time slots.
            </Typography>
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setShowConfirmDialog(false)}>Cancel</Button>
            <Button onClick={handleConfirmBooking} variant="contained" color="primary">
              Confirm
            </Button>
          </DialogActions>
        </Dialog>
      </Container>
    </Layout>
  );
}
