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
} from "@mui/material";
import { useSignUpTutorMutation } from "../services/auth.service";

export default function SignUpTutorPage() {
  const [userName, setUserName] = useState("");
  const [userEmail, setEmailAddress] = useState("");
  const [userPassword, setPassword] = useState("");
  const [userMobileNo, setMobile] = useState("");
  const [hourlyRate, setHourlyRate] = useState("");
  const [qualification, setQualification] = useState("");
  const [experienceYears, setExperienceYears] = useState("");
  const [location, setLocation] = useState("");
  const [description, setsetDescription] = useState("");
  
  const [nameError, setNameError] = useState<boolean | string>(false);
  const [emailError, setEmailError] = useState<boolean | string>(false);
  const [passwordError, setPasswordError] = useState<boolean | string>(false);
  const [mobileNoError, setMobileError] = useState<boolean | string>(false);
  const [hourlyRateError, setHourlyRateError] = useState<boolean | string>(false);
  const [qualificationError, setQualificationError] = useState<boolean | string>(false);
  const [experienceError, setExperienceError] = useState<boolean | string>(false);
  const [locationError, setLocationError] = useState<boolean | string>(false);
    const [descriptionError, setDescriptionError] = useState<boolean | string>(false);
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

  
  const handleSignUpTutor = (e: { preventDefault: () => void; }) => {
    e.preventDefault(); 
    
    let hasErrors = false;

    if (!userName.trim()) {
      setNameError("User name is required.");
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

    if (!description.trim()) {
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
      qualification: qualification.trim(),
      experienceYears: experienceYears,
      location: location.trim(),
      userRole: "TUTOR",
      description:description
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
          onSubmit={handleSignUpTutor} // Changed from onClick to onSubmit
          noValidate
          sx={{ mt: 1, width: "100%" }}
        >
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
            margin="normal"
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
            margin="normal"
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
            margin="normal"
          />

          <TextField
            fullWidth
            required
            label="Summary"
            variant="outlined"
            type="text"
            value={description}
            error={!!descriptionError}
            helperText={descriptionError}
            onChange={(e) => {
              setsetDescription(e.target.value);
              if (e.target.validity.valid) {
                setDescriptionError(false);
              } else {
                setDescriptionError("Please provide Summary");
              }
            }}
            margin="normal"
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
                e.target.value.length == 8 &&
                !isNaN(Number(e.target.value))
              ) {
                setMobileError(false);
              } else {
                setMobileError("Please provide valid singapore mobile number.");
              }
            }}
            margin="normal"
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
            margin="normal"
            InputProps={{
              startAdornment: <InputAdornment position="start">$</InputAdornment>,
              inputProps: { min: 10, max: 200, step: 0.5 }
            }}
          />

       
          <FormControl fullWidth margin="normal" error={!!qualificationError}>
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
            margin="normal"
            inputProps={{
              min: 0,
              max: 50
            }}
          />

          {/* Fixed FormControl for Location */}
          <FormControl fullWidth margin="normal" error={!!locationError}>
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

          <Button
            type="submit" // Changed from onClick to type="submit"
            variant="contained"
            color="primary"
            disabled={result.isLoading}
            fullWidth
            sx={{ mt: 2 }}
          >
            {result.isLoading ? 'Signing Up...' : 'Sign Up as Tutor'}
          </Button>
          
          {result.isError && (
            <Typography color="error" sx={{ mt: 2 }}>
              Sign up failed. Please try again.
              {/* Add more specific error details */}
              
            </Typography>
          )}
          
          <Box
            sx={{
              display: "flex",
              justifyContent: "center",
              width: "100%",
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