const express = require("express");
// const bcrypt = require('bcryptjs');
const User = require("../models/User");

const router = express.Router();

// Register a new user
router.post("/api/register", async (req, res) => {
  console.log("Request Body:", req.body);

  try {
    // const { name, email, password, phoneNumber } = req.body;
    const { name, email, password, phoneNumber, profilePicture } = req.body;

    if (!name || !email || !password || !phoneNumber) {
      console.log("Missing required fields");
      return res.status(400).json({ message: "Missing required fields" });
    }

    // Check if the user already exists
    const existingUser = await User.findOne({ email });
    if (existingUser) {
      console.log("Email already exists");
      return res.status(400).json({ message: "Email already exists" });
    }

    // Hash the password
    // const hashedPassword = await bcrypt.hash(password, 10);

    // Create a new user
    const newUser = new User({
      name,
      email,
      // password: hashedPassword,
      password,
      phoneNumber,
      profilePicture,
    });
    console.log("New User:", newUser);

    // Save the user to the database
    await newUser.save();
    console.log("User saved to Database!");

    res.status(201).json({ message: "User registered successfully" });
  } catch (error) {
    res.status(500).json({ message: "Server error", error });
  }
});

module.exports = router;
