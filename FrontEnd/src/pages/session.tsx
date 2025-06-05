import React, { useState } from 'react';
import {
  Container,
  Typography,
  Box,
  Paper,
  Grid,
  Card,
  CardContent,
  Chip,
  Stack,
  Divider,
} from '@mui/material';
import { DateCalendar } from '@mui/x-date-pickers/DateCalendar';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { format, isSameDay } from 'date-fns';
import Layout from '../components/Layout';
import Sidebar from '../components/Layout/sidebar';

interface Session {
  id: string;
  date: Date;
  startTime: string;
  endTime: string;
  subject: string;
  student: {
    name: string;
    email: string;
  };
  tutor: {
    name: string;
    email: string;
  };
  status: 'upcoming' | 'completed' | 'cancelled';
}

// Mock data for demonstration
const MOCK_SESSIONS: Session[] = [
  {
    id: '1',
    date: new Date(2024, 2, 15), // March 15, 2024
    startTime: '09:00',
    endTime: '10:30',
    subject: 'Mathematics',
    student: {
      name: 'John Doe',
      email: 'john@example.com'
    },
    tutor: {
      name: 'Alice Smith',
      email: 'alice@example.com'
    },
    status: 'upcoming'
  },
  {
    id: '2',
    date: new Date(2024, 2, 15), // March 15, 2024
    startTime: '14:00',
    endTime: '15:30',
    subject: 'Physics',
    student: {
      name: 'John Doe',
      email: 'john@example.com'
    },
    tutor: {
      name: 'Bob Wilson',
      email: 'bob@example.com'
    },
    status: 'upcoming'
  },
  {
    id: '3',
    date: new Date(2024, 2, 16), // March 16, 2024
    startTime: '10:00',
    endTime: '11:30',
    subject: 'Chemistry',
    student: {
      name: 'John Doe',
      email: 'john@example.com'
    },
    tutor: {
      name: 'Carol Brown',
      email: 'carol@example.com'
    },
    status: 'upcoming'
  }
];

const SessionPage: React.FC = () => {
  const [selectedDate, setSelectedDate] = useState<Date | null>(new Date());

  const getSessionsForDate = (date: Date | null) => {
    if (!date) return [];
    return MOCK_SESSIONS.filter(session => isSameDay(session.date, date));
  };

  const getStatusColor = (status: Session['status']) => {
    switch (status) {
      case 'upcoming':
        return 'primary';
      case 'completed':
        return 'success';
      case 'cancelled':
        return 'error';
      default:
        return 'default';
    }
  };

  return (
    <Layout isLoading={false}>
      <Sidebar />
      <Container maxWidth="lg" sx={{ py: 4, ml: '240px' }}>
        <Typography variant="h4" gutterBottom>
          My Sessions
        </Typography>

        <Grid container spacing={3}>
          {/* Calendar Section */}
          <Grid item xs={12} md={4}>
            <Paper sx={{ p: 2 }}>
              <LocalizationProvider dateAdapter={AdapterDateFns}>
                <DateCalendar
                  value={selectedDate}
                  onChange={(newDate) => setSelectedDate(newDate)}
                  sx={{ width: '100%' }}
                />
              </LocalizationProvider>
            </Paper>
          </Grid>

          {/* Sessions List Section */}
          <Grid item xs={12} md={8}>
            <Paper sx={{ p: 2 }}>
              <Typography variant="h6" gutterBottom>
                {selectedDate ? `Sessions for ${format(selectedDate, 'MMMM d, yyyy')}` : 'Select a date'}
              </Typography>
              
              <Stack spacing={2}>
                {getSessionsForDate(selectedDate).map((session) => (
                  <Card key={session.id}>
                    <CardContent>
                      <Grid container spacing={2}>
                        <Grid item xs={12}>
                          <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 1 }}>
                            <Typography variant="h6">
                              {session.subject}
                            </Typography>
                            <Chip
                              label={session.status.charAt(0).toUpperCase() + session.status.slice(1)}
                              color={getStatusColor(session.status)}
                              size="small"
                            />
                          </Box>
                        </Grid>

                        <Grid item xs={12}>
                          <Typography variant="body2" color="text.secondary">
                            Time: {session.startTime} - {session.endTime}
                          </Typography>
                        </Grid>

                        <Grid item xs={12}>
                          <Divider sx={{ my: 1 }} />
                        </Grid>

                        <Grid item xs={6}>
                          <Typography variant="subtitle2">Student</Typography>
                          <Typography variant="body2">{session.student.name}</Typography>
                          <Typography variant="body2" color="text.secondary">
                            {session.student.email}
                          </Typography>
                        </Grid>

                        <Grid item xs={6}>
                          <Typography variant="subtitle2">Tutor</Typography>
                          <Typography variant="body2">{session.tutor.name}</Typography>
                          <Typography variant="body2" color="text.secondary">
                            {session.tutor.email}
                          </Typography>
                        </Grid>
                      </Grid>
                    </CardContent>
                  </Card>
                ))}

                {getSessionsForDate(selectedDate).length === 0 && (
                  <Typography color="text.secondary" align="center">
                    No sessions scheduled for this date
                  </Typography>
                )}
              </Stack>
            </Paper>
          </Grid>
        </Grid>
      </Container>
    </Layout>
  );
};

export default SessionPage;
