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
} from "@mui/material";
import { useSignUpMutation } from "../services/auth.service";

export default function SignUpPage() {
  const [userName, setUserName] = useState("");
  const [userEmail, setEmailAddress] = useState("");
  const [userPassword, setPassword] = useState("");
  const [userMobileNo, setMobile] = useState("");
  const [nameError, setNameError] = useState<boolean | string>(false);
  const [emailError, setEmailError] = useState<boolean | string>(false);
  const [passwordError, setPasswordError] = useState<boolean | string>(false);
  const [mobileNoError, setMobileError] = useState<boolean | string>(false);
  const [signUp, result] = useSignUpMutation();

  const handleSignUp = () => {
    signUp({
      userName,
      userEmail,
      userPassword,
      userMobileNo,
      userRole: "STUDENT",
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
    <Container component="main" maxWidth="xs">
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
          Sign Up
        </Typography>
        <Box
          component="form"
          onSubmit={handleSignUp}
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

          <Button
            variant="contained"
            color="primary"
            onClick={handleSignUp}
            disabled={result.isLoading}
            fullWidth
          >
            Sign Up and Auto Login
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
    </Container>
  );
}
