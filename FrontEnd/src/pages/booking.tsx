import React from "react";
import { navigate } from "gatsby";

import { selectAuthSlice } from "../state/auth/slice";
import { useSelector } from "react-redux";


export default function Booking() {
  const { isLoggedIn, userInfo } = useSelector((state) =>
    selectAuthSlice(state)
  );

 
  return null;
}
