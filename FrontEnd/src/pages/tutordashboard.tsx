import React, { useState, useEffect } from "react";
import {
  Container,
  Typography,
  Box,
  Grid,
  Paper,
  List,
  ListItem,
  ListItemText,
  Divider,
} from "@mui/material";
import Layout from "../components/Layout";

function TutorDashboard() {
  return (
    <Layout isLoading={false}>
      <Container maxWidth="xl" sx={{ mt: 4, mb: 4 }}>
        {/* First Row */}
        <Grid container spacing={3} sx={{ mb: 4 }}>
          <Grid>
            <Paper
              sx={{
                p: 2,
                display: 'flex',
                flexDirection: 'column',
                minHeight: 240,
              }}
            >
              <Typography variant="h6" sx={{ mb: 2 }}>
                Pending Booking Requests
              </Typography>
              <List>
                {/* Replace with actual pending bookings data */}
                <ListItem>
                  <ListItemText
                    primary="Student: John Doe"
                    secondary="Subject: Mathematics | Time: Mon, 2:00 PM"
                  />
                </ListItem>
                <Divider />
              </List>
            </Paper>
          </Grid>
        </Grid>

        {/* Second Row */}
        <Grid container spacing={3}>
          {/* Upcoming Sessions */}
          <Grid>
            <Paper
              sx={{
                p: 2,
                display: 'flex',
                flexDirection: 'column',
                minHeight: 240,
              }}
            >
              <Typography variant="h6" sx={{ mb: 2 }}>
                Upcoming Sessions
              </Typography>
              <List>
                {/* Replace with actual upcoming sessions data */}
                <ListItem>
                  <ListItemText
                    primary="Mathematics with Alice"
                    secondary="Thursday, 3:00 PM"
                  />
                </ListItem>
                <Divider />
              </List>
            </Paper>
          </Grid>

          {/* Confirmed Students */}
          <Grid>
            <Paper
              sx={{
                p: 2,
                display: 'flex',
                flexDirection: 'column',
                minHeight: 240,
              }}
            >
              <Typography variant="h6" sx={{ mb: 2 }}>
                Confirmed Students
              </Typography>
              <List>
                {/* Replace with actual confirmed students data */}
                <ListItem>
                  <ListItemText
                    primary="Alice Smith"
                    secondary="Mathematics - Weekly Sessions"
                  />
                </ListItem>
                <Divider />
              </List>
            </Paper>
          </Grid>
        </Grid>
      </Container>
    </Layout>
  );
}

export default TutorDashboard;
