import React, { useState } from "react";
import { navigate } from "gatsby";

import { selectAuthSlice } from "../state/auth/slice";
import { useSelector } from "react-redux";
import { createBooking } from "../services/booking.service";

export default function Booking() {
  const { isLoggedIn, userInfo } = useSelector((state) =>
    selectAuthSlice(state)
  );

  const [form, setForm] = useState({
    studentId: "",
    slotId: "",
    subjectName: ""
  });
  const [result, setResult] = useState<any>(null);
  const [error, setError] = useState<string>("");

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    try {
      const res = await createBooking(form);
      setResult(res);
    } catch (err: any) {
      setError(err.message || "Failed to create booking");
    }
  };

  return (
    <div style={{ padding: 24 }}>
      <h2>Booking Demo</h2>
      <form onSubmit={handleSubmit}>
        <input name="studentId" placeholder="Student ID" value={form.studentId} onChange={handleChange} />
        <input name="slotId" placeholder="Slot ID" value={form.slotId} onChange={handleChange} />
        <input name="subjectName" placeholder="Subject Name" value={form.subjectName} onChange={handleChange} />
        <button type="submit">Book</button>
      </form>
      {error && <div style={{ color: 'red' }}>{error}</div>}
      {result && <pre>{JSON.stringify(result, null, 2)}</pre>}
    </div>
  );
}
