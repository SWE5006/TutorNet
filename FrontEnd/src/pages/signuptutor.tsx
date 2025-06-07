import React, { useEffect, useState } from "react";
import { navigate } from "gatsby";
import {
  TextField,
  Button,
  Typography,
  Container,
  Paper,
  Box,
  Link,
  MenuItem,
  Select,
  FormControl,
  InputLabel,
  FormHelperText,
  InputAdornment,
  Stack,
} from "@mui/material";
import { useSignUpTutorMutation } from "../services/auth.service";
import Chip from "@mui/material/Chip";

export default function SignUpTutorPage() {
  const [userName, setUserName] = useState("");
  const [userEmail, setEmailAddress] = useState("");
  const [userPassword, setPassword] = useState("");
  const [userMobileNo, setMobile] = useState("");
  const [hourlyRate, setHourlyRate] = useState("");
  const [qualification, setQualification] = useState("");
  const [experienceYears, setExperienceYears] = useState("");
  const [location, setLocation] = useState("");
  const [bio, setBio] = useState("");

  const [nameError, setNameError] = useState<boolean | string>(false);
  const [emailError, setEmailError] = useState<boolean | string>(false);
  const [passwordError, setPasswordError] = useState<boolean | string>(false);
  const [mobileNoError, setMobileError] = useState<boolean | string>(false);
  const [hourlyRateError, setHourlyRateError] = useState<boolean | string>(false);
  const [qualificationError, setQualificationError] = useState<boolean | string>(false);
  const [experienceError, setExperienceError] = useState<boolean | string>(false);
  const [locationError, setLocationError] = useState<boolean | string>(false);
  const [descriptionError, setDescriptionError] = useState<boolean | string>(false);
  const [subjectsError, setSubjectsError] = useState<boolean | string>(false);

  const [teachingSubjects, setTeachingSubjects] = useState<string[]>([]);
  const [signupTutor, result] = useSignUpTutorMutation();

  const qualifications = [
    "Bachelor's Degree",
    "Master's Degree",
    "PhD",
    "Diploma",
    "A-Levels",
    "Professional Certificate",
    "Teaching Qualification",
    "Other"
  ];

  const singaporeLocations = [
    "Central",
    "North",
    "South", 
    "East",
    "West",
    "North-East",
    "Ang Mo Kio",
    "Bedok",
    "Bishan",
    "Bukit Batok",
    "Bukit Merah",
    "Bukit Panjang",
    "Bukit Timah",
    "Choa Chu Kang",
    "Clementi",
    "Geylang",
    "Hougang",
    "Jurong East",
    "Jurong West",
    "Kallang",
    "Marine Parade",
    "Pasir Ris",
    "Punggol",
    "Queenstown",
    "Sembawang",
    "Sengkang",
    "Serangoon",
    "Tampines",
    "Toa Payoh",
    "Woodlands",
    "Yishun"
  ];

  const subjectOptions = [
    "Physics",
    "Chemistry",
    "Mathematics",
    "English",
    "Chinese",
    "Malay",
    "History",
    "Geography",
    "Biology",
    "Literature",
    "Economics",
    "Social Studies",
    "Computer Science",
    "Art",
    "Music",
    "Physical Education",
    "Other"
  ];

  const handleSignUpTutor = (e: { preventDefault: () => void; }) => {
    e.preventDefault(); 
    
    let hasErrors = false;

    if (!userName.trim()) {
      setNameError("User name is required.");
      hasErrors = true;
    }

    if (teachingSubjects.length === 0) {
      setSubjectsError("Please select at least one subject.");
      hasErrors = true;
    }

    if (!userEmail.trim()) {
      setEmailError("Email is required.");
      hasErrors = true;
    }

    if (!userPassword.trim()) {
      setPasswordError("Password is required.");
      hasErrors = true;
    }

    if (!userMobileNo.trim()) {
      setMobileError("Mobile number is required.");
      hasErrors = true;
    }

    if (!hourlyRate.trim()) {
      setHourlyRateError("Hourly rate is required.");
      hasErrors = true;
    }

    if (!qualification.trim()) {
      setQualificationError("Qualification is required.");
      hasErrors = true;
    }

    if (!experienceYears.trim()) {
      setExperienceError("Experience years is required.");
      hasErrors = true;
    }

    if (!location.trim()) {
      setLocationError("Location is required.");
      hasErrors = true;
    }

    if (!bio.trim()) {
      setDescriptionError("Description is required.");
      hasErrors = true;
    }
    
    if (hasErrors) {
      console.log('Form has errors, not submitting');
      return;
    }
    
    signupTutor({
      username: userName.trim(),
      emailAddress: userEmail.trim(),
      password: userPassword.trim(),
      mobileNumber: userMobileNo.trim(),
      hourlyRate: hourlyRate,
      education: qualification.trim(),
      experience: experienceYears,
      location: location.trim(),
      userRole: "TUTOR",
      teachingSubjects: teachingSubjects,
      bio: bio
    });
  };

  useEffect(() => {
    if (result.isSuccess) {
      console.log('Signup successful:', result.data);
      navigate("/login");
    } else if (result.isError) {
      console.error("Signup error:", result.error);
    }
  }, [result]);

  return (
    <Container component="main" maxWidth="sm">
      <Paper
        elevation={6}
        sx={{
          mt: 8,
          p: 4,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          borderRadius: 2,
        }}
      >
        <Typography variant="h4" textAlign="center">
          Sign Up as Tutor
        </Typography>
        <Box
          component="form"
          onSubmit={handleSignUpTutor}
          noValidate
          sx={{ mt: 1, width: "100%" }}
        >
          <Stack spacing={2}>
            <TextField
              required
              fullWidth
              label="User Name"
              variant="outlined"
              value={userName}
              error={!!nameError}
              helperText={nameError}
              onChange={(e) => {
                setUserName(e.target.value);
                if (e.target.value.length >= 1) {
                  setNameError(false);
                } else {
                  setNameError("User name is required.");
                }
              }}
            />

            <TextField
              fullWidth
              required
              label="Email Address"
              variant="outlined"
              type="email"
              value={userEmail}
              inputProps={{
                type: "email",
              }}
              error={!!emailError}
              helperText={emailError}
              onChange={(e) => {
                setEmailAddress(e.target.value);
                if (e.target.validity.valid) {
                  setEmailError(false);
                } else {
                  setEmailError("Please provide valid email address.");
                }
              }}
            />

            <TextField
              required
              fullWidth
              label="Password"
              variant="outlined"
              type="password"
              value={userPassword}
              error={!!passwordError}
              helperText={passwordError}
              onChange={(e) => {
                setPassword(e.target.value);
                if (
                  !/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/.test(
                    e.target.value
                  )
                ) {
                  setPasswordError(
                    "Invalid password, your password must be: Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character"
                  );
                } else {
                  setPasswordError(false);
                }
              }}
            />

            <TextField
              fullWidth
              required
              label="Summary"
              variant="outlined"
              type="text"
              value={bio}
              error={!!descriptionError}
              helperText={descriptionError}
              onChange={(e) => {
                setBio(e.target.value);
                if (e.target.validity.valid) {
                  setDescriptionError(false);
                } else {
                  setDescriptionError("Please provide Summary");
                }
              }}
            />

            <TextField
              required
              fullWidth
              label="Contact Number"
              variant="outlined"
              type="text"
              value={userMobileNo}
              error={!!mobileNoError}
              helperText={mobileNoError}
              onChange={(e) => {
                setMobile(e.target.value);
                if (
                  e.target.value.length === 8 &&
                  !isNaN(Number(e.target.value))
                ) {
                  setMobileError(false);
                } else {
                  setMobileError("Please provide valid singapore mobile number.");
                }
              }}
            />

            <TextField
              required
              fullWidth
              label="Hourly Rate"
              variant="outlined"
              type="number"
              value={hourlyRate}
              error={!!hourlyRateError}
              helperText={hourlyRateError}
              onChange={(e) => {
                setHourlyRate(e.target.value);
                const rate = parseFloat(e.target.value);
                if (rate >= 10 && rate <= 200) {
                  setHourlyRateError(false);
                } else {
                  setHourlyRateError("Please provide a valid hourly rate between $10 and $200.");
                }
              }}
              InputProps={{
                startAdornment: <InputAdornment position="start">$</InputAdornment>,
                inputProps: { min: 10, max: 200, step: 0.5 }
              }}
            />

            <FormControl fullWidth error={!!qualificationError}>
              <InputLabel id="qualification-label">Qualification</InputLabel>
              <Select
                labelId="qualification-label"
                value={qualification}
                label="Qualification"
                required
                onChange={(e) => {
                  setQualification(e.target.value);
                  if (e.target.value) {
                    setQualificationError(false);
                  } else {
                    setQualificationError("Please select your qualification.");
                  }
                }}
              >
                {qualifications.map((qual) => (
                  <MenuItem key={qual} value={qual}>
                    {qual}
                  </MenuItem>
                ))}
              </Select>
              {qualificationError && (
                <FormHelperText>{qualificationError}</FormHelperText>
              )}
            </FormControl>

            <TextField
              required
              fullWidth
              label="Years of Experience"
              variant="outlined"
              type="number"
              value={experienceYears}
              error={!!experienceError}
              helperText={experienceError}
              onChange={(e) => {
                setExperienceYears(e.target.value);
                const years = parseInt(e.target.value);
                if (years >= 0 && years <= 50) {
                  setExperienceError(false);
                } else {
                  setExperienceError("Please provide valid years of experience (0-50).");
                }
              }}
              inputProps={{
                min: 0,
                max: 50
              }}
            />

            <FormControl fullWidth error={!!locationError}>
              <InputLabel id="location-label">Preferred Location</InputLabel>
              <Select
                labelId="location-label"
                value={location}
                label="Preferred Location"
                required
                onChange={(e) => {
                  setLocation(e.target.value);
                  if (e.target.value) {
                    setLocationError(false);
                  } else {
                    setLocationError("Please select your preferred location.");
                  }
                }}
              >
                {singaporeLocations.map((loc) => (
                  <MenuItem key={loc} value={loc}>
                    {loc}
                  </MenuItem>
                ))}
              </Select>
              {locationError && (
                <FormHelperText>{locationError}</FormHelperText>
              )}
            </FormControl>

            <FormControl fullWidth error={!!subjectsError}>
              <InputLabel id="subjects-label">Teaching Subjects</InputLabel>
              <Select
                labelId="subjects-label"
                multiple
                value={teachingSubjects}
                onChange={(e) => {
                  const value = typeof e.target.value === "string"
                    ? e.target.value.split(",")
                    : e.target.value;
                  setTeachingSubjects(value);
                  if (value.length > 0) {
                    setSubjectsError(false);
                  } else {
                    setSubjectsError("Please select at least one subject.");
                  }
                }}
                label="Teaching Subjects"
                renderValue={(selected) => (
                  <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                    {(selected as string[]).map((value) => (
                      <Chip key={value} label={value} />
                    ))}
                  </Box>
                )}
              >
                {subjectOptions.map((subject) => (
                  <MenuItem key={subject} value={subject}>
                    {subject}
                  </MenuItem>
                ))}
              </Select>
              {subjectsError && (
                <FormHelperText>{subjectsError}</FormHelperText>
              )}
            </FormControl>

            <Button
              type="submit"
              variant="contained"
              color="primary"
              disabled={result.isLoading}
              fullWidth
            >
              {result.isLoading ? 'Signing Up...' : 'Sign Up as Tutor'}
            </Button>
          </Stack>

          {result.isError && (
            <Typography color="error" sx={{ mt: 2 }}>
              Sign up failed. Please try again.
            </Typography>
          )}

          <Box
            sx={{
              display: "flex",
              justifyContent: "center",
              inlineSize: "100%",
              mt: 2,
            }}
          >
            <Link href="/login" variant="body2">
              {"Already have an account? Login"}
            </Link>
          </Box>
        </Box>
      </Paper>
    </Container>
  );
}