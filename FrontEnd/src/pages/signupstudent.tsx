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
} from "@mui/material";
import { useSignUpStudentMutation } from "../services/auth.service";
import { Box as MuiBox } from "@mui/material";

export default function SignUpPage() {
  const [userName, setUserName] = useState("");
  const [userEmail, setEmailAddress] = useState("");
  const [userPassword, setPassword] = useState("");
  const [userMobileNo, setMobile] = useState("");
  const [userAge, setUserAge] = useState("");
  const [userClassLevel, setUserClassLevel] = useState("");
  const [nameError, setNameError] = useState<boolean | string>(false);
  const [emailError, setEmailError] = useState<boolean | string>(false);
  const [passwordError, setPasswordError] = useState<boolean | string>(false);
  const [mobileNoError, setMobileError] = useState<boolean | string>(false);
  const [ageError, setAgeError] = useState<boolean | string>(false);
  const [classLevelError, setClassLevelError] = useState<boolean | string>(false);
  const [signupStudent, result] = useSignUpStudentMutation();

  const classLevels = [
    "Primary 1",
    "Primary 2", 
    "Primary 3",
    "Primary 4",
    "Primary 5",
    "Primary 6",
    "Secondary 1",
    "Secondary 2",
    "Secondary 3",
    "Secondary 4",
    "Junior College 1",
    "Junior College 2",
    "University",
    "Adult Learning"
  ];

 // Fixed handleSignUp function
  const handleSignUpStudent = (e: { preventDefault: () => void; }) => {
    e.preventDefault(); // Prevent default form submission
    
 

    // Validate all required fields
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

    // Don't submit if there are errors
    if (hasErrors) {
      console.log('Form has errors, not submitting');
      return;
    }

    // Submit the form
    signupStudent({
      username: userName.trim(),
      emailAddress: userEmail.trim(),
      password: userPassword.trim(),
      mobileNumber: userMobileNo.trim(),
      age: userAge,
      classLevel:userClassLevel,
      userRole: "TUTOR",
    });
  };

  useEffect(() => {
    if (result.isSuccess) {
      navigate("/login");
    } else if (result.isError) {
      console.error("Signup error:", result.error);
    }
  }, [result]);

  return (
    <Container 
      maxWidth="lg" 
      sx={{ 
        height: "100vh", 
        display: "flex", 
        alignItems: "center",
        pl: 3,
      }}
    >
      <MuiBox
        sx={{
          display: "flex",
          justifyContent: "flex-end",
          alignItems: "center",
          width: "100%",
          height: "650px",
          bgcolor: "background.paper",
          borderRadius: 3,
          boxShadow: 3,
          overflow: "hidden",
          backgroundImage: `url('/images/signuptutor.jpg')`,
          backgroundSize: 'cover',
          backgroundPosition: 'center',
          backgroundRepeat: 'no-repeat',
        }}
      >
        <Paper
          elevation={6}
          sx={{
            p: 4,
            width: "100%",
            maxWidth: 400,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            borderRadius: 2,
          }}
        >
          <Typography variant="h4" textAlign="center">
            Sign Up as Student
          </Typography>
          
          <Box
            component="form"
            onSubmit={handleSignUpStudent}
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
              label="Age"
              variant="outlined"
              type="number"
              value={userAge}
              error={!!ageError}
              helperText={ageError}
              onChange={(e) => {
                setUserAge(e.target.value);
                const age = parseInt(e.target.value);
                if (age >= 5 && age <= 100) {
                  setAgeError(false);
                } else {
                  setAgeError("Please provide a valid age between 5 and 100.");
                }
              }}
              margin="normal"
              inputProps={{
                min: 5,
                max: 100
              }}
            />

     
          <InputLabel id="class-level-label">Class Level</InputLabel>
          <Select
            fullWidth
            labelId="class-level-label"
            value={userClassLevel}
            label="Class Level"
            onChange={(e) => {
              setUserClassLevel(e.target.value);
              if (e.target.value) {
                setClassLevelError(false);
              } else {
                setClassLevelError("Please select a class level.");
              }
            }}
           sx={{ marginBottom: 2 }} 
          >
            {classLevels.map((level) => (
              <MenuItem key={level} value={level}>
                {level}
              </MenuItem>
            ))}
          </Select>
          {classLevelError && (
            <FormHelperText>{classLevelError}</FormHelperText>
          )}
 
            <Button
              variant="contained"
              color="primary"
              onClick={handleSignUpStudent}
              disabled={result.isLoading}
              fullWidth
            >
              Sign Up AS Student
            </Button>
            {result.isError && (
              <Typography color="error">
                Sign up failed. Please try again.
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
      </MuiBox>
    </Container>
  );
}