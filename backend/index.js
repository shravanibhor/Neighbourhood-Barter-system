// Setup
const express = require("express");
const app = express();
require("dotenv").config();
const cors = require("cors");
const connectDB = require("./src/config/db.js");
const userRegisterRoute = require("./src/routes/userRegister");

// Middleware
app.use(express.json());
app.use(cors());

// Connect to MongoDB
connectDB();

// If client sends a GET request to /api
app.get("/api", (req, res) => {
  res.json({ message: "/api says: Hello from server!" });
});

// Routes
app.use(userRegisterRoute);

// Start server
const port = process.env.PORT || 5000;
app.listen(port, "0.0.0.0", () => {
  console.log(`Server is listening on port ${port}...`);
});
