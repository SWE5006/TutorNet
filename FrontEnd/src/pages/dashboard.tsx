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
} from "@mui/material";
import Layout from "../components/Layout";
import Sidebar from "../components/Layout/sidebar";


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
    id: "tutor1",
    name: "Alice Wonderland",
    subject: "Mathematics",
    description:
      "Experienced in algebra, calculus, and geometry. Passionate about making math fun!",
    location: "Singapore",
    rating: 4.8,
  },
  {
    id: "tutor2",
    name: "Bob The Builder",
    subject: "Physics",
    description:
      "Specializes in classical mechanics and electromagnetism. Prepares students for competitive exams.",
    location: "Jurong East",
    rating: 4.5,
  },
  {
    id: "tutor3",
    name: "Charlie Chaplin",
    subject: "English",
    description:
      "Focuses on essay writing, grammar, and literature analysis. Helps improve verbal communication.",
    location: "Tampines",
    rating: 4.9,
  },
  {
    id: "tutor4",
    name: "Diana Prince",
    subject: "Chemistry",
    description:
      "Expert in organic and inorganic chemistry. Provides clear explanations and problem-solving strategies.",
    location: "Ang Mo Kio",
    rating: 4.7,
  },
  {
    id: "tutor5",
    name: "Eve Adams",
    subject: "Mathematics",
    description:
      "Certified tutor for primary and secondary school mathematics. Patient and encouraging.",
    location: "Woodlands",
    rating: 4.6,
  },
  {
    id: "tutor6",
    name: "Frank Ocean",
    subject: "Biology",
    description:
      "Covers cell biology, genetics, and ecology. Makes complex topics easy to understand.",
    location: "Novena",
    rating: 4.4,
  },
  {
    id: "tutor7",
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

  // const [TUTORS, setTUTORS] = useState<Tutor[]>([]);
  // useEffect(() => {
  //   const fetchTutors = async () => {
  //     try {
  //       setLoading(true);
  //       const response = await fetch('/api/tutors/all'); 
        
  //       if (!response.ok) {
  //         throw new Error('Failed to fetch tutors');
  //       }
        
  //       const tutorsData: Tutor[] = await response.json();
  //       setTUTORS(tutorsData);
  //     } catch (err) {
  //       setError(err instanceof Error ? err.message : 'An error occurred');
  //     } finally {
  //       setLoading(false);
  //     }
  //   };
  //       fetchTutors();
  // }, []);

  const ALL_SUBJECTS: string[] = [
  "All Subjects",
  ...new Set(TUTORS.map((tutor) => tutor.subject)),
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
          <Grid container spacing={3}>
            {filteredTutors.map((tutor) => (
              <Grid width="100%" key={tutor.id}>
                 <Box sx={{ position: 'relative'}}>
                  {/* Photo placeholder */}
                  <Box
                    sx={{
                      width:"16%",
                      height: 164,
                      position: 'absolute',
                      top: 16,
                      float:"left",
                      bgcolor: 'grey.300',
                      fontSize: 14
                    }}
                  >
                    IMG
                  </Box>
                  
                </Box >
                <Card
                  sx={{
                    height: "100%",
                    display: "flex",
                    width:"82%",
                    flexDirection: "column",
                    boxShadow: 6,
                    float:"right"
                  }}
                >
                  <CardContent sx={{ flexGrow: 1 }}>
               
                    <Typography variant="h6" component="div" gutterBottom>
                      {tutor.name}
                    </Typography>
                    <Typography
                      variant="subtitle1"
                      color="primary"
                      sx={{ mb: 1 }}
                    >
                      {tutor.subject}
                    </Typography>
                    <Typography
                      variant="body2"
                      color="text.secondary"
                      sx={{ mb: 1.5 }}
                    >
                      {tutor.description}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      <Box component="span" fontWeight="medium">
                        Location:
                      </Box>{" "}
                      {tutor.location}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      <Box component="span" fontWeight="medium">
                        Rating:
                      </Box>{" "}
                      {tutor.rating} / 5
                    </Typography>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        )}
      </Container>
    </Layout>
  );
}

export default TutorListPage;
