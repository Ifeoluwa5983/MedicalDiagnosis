/** @format */
import React, { useReducer, useState } from "react";
import { useNavigate } from "react-router-dom";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { ResultValue } from "./Result";

import "./App.css";

export const baseUrl = "http://localhost:8080";

const style = {
  position: "absolute",
  top: "40%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  boxShadow: 24,
  p: 4,
  textAlign: "center",
};

const AppModal = ({ open, handleClose, title, description, children }) => {
  return (
    <Modal
      open={open}
      handleClose={handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
        <Typography id="modal-modal-title" variant="h6" component="h2">
          {title}
        </Typography>
        <Typography id="modal-modal-description" sx={{ mt: 2 }}>
          {description}
        </Typography>
        <div>{children}</div>
      </Box>
    </Modal>
  );
};

const Content = () => {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const [userData, setUserData] = useState();
  const [loading, setLoading] = useState("....");
  const [formInput, setFormInput] = useReducer(
    (state, newState) => ({ ...state, ...newState }),
    {
      symptoms: "",
      gender: "",
      yearOfBirth: "",
      token: "",
    }
  );

  const handleSubmit = (e) => {
    e.preventDefault();

    const data = {
      symptoms: formInput.symptoms,
      gender: formInput.gender,
      yearOfBirth: Number(formInput.yearOfBirth),
      token:
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im8uaWZlb2x1d2FoQGdtYWlsLmNvbSIsInJvbGUiOiJVc2VyIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvc2lkIjoiMTA5MjMiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3ZlcnNpb24iOiIyMDAiLCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL2xpbWl0IjoiOTk5OTk5OTk5IiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9tZW1iZXJzaGlwIjoiUHJlbWl1bSIsImh0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbGFuZ3VhZ2UiOiJlbi1nYiIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvZXhwaXJhdGlvbiI6IjIwOTktMTItMzEiLCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL21lbWJlcnNoaXBzdGFydCI6IjIwMjItMDctMTUiLCJpc3MiOiJodHRwczovL3NhbmRib3gtYXV0aHNlcnZpY2UucHJpYWlkLmNoIiwiYXVkIjoiaHR0cHM6Ly9oZWFsdGhzZXJ2aWNlLnByaWFpZC5jaCIsImV4cCI6MTY1ODE0MTk4NCwibmJmIjoxNjU4MTM0Nzg0fQ.oxdrlbJxHrwr51cRiFE5GYbsEHQqAir5-Q4APh8Aqv4",
    };

    axios
      .post(`${baseUrl}/diagonise`, data)
      .then(
        (res) => (
          res.status === 200 && handleOpen(),
          toast.success("Successfully sent diagonistics"),
          setUserData(res.data)
        )
      )
      .catch((e) => (e, console.log(e)));
  };

  const handleInput = (event) => {
    const name = event.target.name;
    const newValue = event.target.value;
    setFormInput({ [name]: newValue });
  };


  return (
    <div>
      {userData ? (
        <ResultValue userData={userData} />
      ) : (
        <div className="form">
          <form onSubmit={handleSubmit}>
            <div className="input_field">
              <div className="input__field__value">
                <TextField
                  id="symptom_id"
                  label="Symptoms"
                  variant="outlined"
                  style={{ width: "40rem" }}
                  onChange={handleInput}
                  autoComplete="off"
                  name="symptoms"
                />
              </div>
              <div className="input__field__value">
                <TextField
                  id="gender"
                  label="Gender"
                  variant="outlined"
                  text-field
                  style={{ width: "40rem" }}
                  onChange={handleInput}
                  autoComplete="off"
                  name="gender"
                />
              </div>
              <div className="input__field__value">
                <div>
                  <TextField
                    id="yearOfBirth"
                    label="Year of Birth"
                    variant="outlined"
                    text-field
                    style={{ width: "40rem" }}
                    onChange={handleInput}
                    autoComplete="off"
                    name="yearOfBirth"
                    type="text"
                  />{" "}
                </div>
              </div>
            </div>
            <div className="btn">
              <Button
                variant="contained"
                size="large"
                type="submit"
                loading={loading}
              >
                diagnose
              </Button>
            </div>
          </form>

          <div>
            <AppModal
              title="Your result is ready!"
              description="Please proceed to next steps to view details"
              open={open}
            >
              <div className="modal__close">
                <button className="modal__btn-close" onClick={handleClose}>
                  close
                </button>
              </div>
              <div>
                <Button variant="contained"></Button>
              </div>
            </AppModal>
          </div>
        </div>
      )}
    </div>
  );
};

function App({ data }) {
  return (
    <div className="App">
      <div className="header">
        <h1>The best place to diagnose your health</h1>
      </div>
      <Content />
    </div>
  );
}

export default App;
