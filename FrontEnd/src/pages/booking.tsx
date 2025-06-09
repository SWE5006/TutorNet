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
  slotId: string; // Add a unique slotId for each time slot
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
          availability: [
            { slotId: "student1-slot1", dayOfWeek: 1, startTime: "09:00", endTime: "11:00" },
            { slotId: "student1-slot2", dayOfWeek: 3, startTime: "14:00", endTime: "16:00" }
          ],
          subjects: []
        },
        tutor: {
          id: "tutor1",
          name: "Alice Smith",
          email: "alice@example.com",
          availability: [
            { slotId: "tutor1-slot1", dayOfWeek: 1, startTime: "09:00", endTime: "12:00" },
            { slotId: "tutor1-slot2", dayOfWeek: 3, startTime: "13:00", endTime: "17:00" }
          ],
          subjects: []
        },
        commonSubjects: [],
        commonTimeSlots: [
          { slotId: "common-slot1", dayOfWeek: 1, startTime: "09:00", endTime: "11:00" },
          { slotId: "common-slot2", dayOfWeek: 3, startTime: "14:00", endTime: "16:00" }
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
      if (!selectedMatch) return;
      for (const slot of selectedMatch.commonTimeSlots) {
        await createBooking({
          studentId: selectedMatch.student.id,
          slotId: slot.slotId, // Make sure slotId exists in your slot object
          subjectName: selectedMatch.commonSubjects ? selectedMatch.commonSubjects[0] : "" // Use first common subject
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
          Booking List
        </Typography>
        
        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}

        <Grid container spacing={3}>
          {matches.map((match) => (
            <Grid key={match.id}>
              <Card>
                <CardContent>
                  <Grid container spacing={2}>
                    <Grid>
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

                    <Grid>
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

                    <Grid>
                      <Divider sx={{ my: 2 }} />
                      <Typography variant="h6" gutterBottom>
                        Common Time Slots
                      </Typography>
                      <Stack direction="row" spacing={1} sx={{ mt: 1 }}>
                        {match.commonTimeSlots.map((slot: { dayOfWeek: string | number; startTime: any; endTime: any; }, index: React.Key | null | undefined) => (
                          <Chip
                            key={index}
                            label={`${DAYS_OF_WEEK[Number(slot.dayOfWeek)]} ${slot.startTime}-${slot.endTime}`}
                            color="primary"
                            variant="outlined"
                          />
                        ))}
                      </Stack>
                    </Grid>

                    <Grid>
                      <Box sx={{ display: 'flex', justifyContent: 'flex-end', mt: 2 }}>
                        <Button
                          variant="contained"
                          color="primary"
                          onClick={() => handleConfirmMatch(match)}
                        >
                          Accept Booking
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
