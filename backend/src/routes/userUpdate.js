const express = require("express");
const User = require("../models/User");

const router = express.Router();

// Update user location (called when user clicks "Update Location" button)
router.put("/api/update-location/:userId", async (req, res) => {
  try {
    const { latitude, longitude } = req.body;
    const userId = req.params.userId;

    if (!latitude || !longitude) {
      return res.status(400).json({ message: "Latitude and longitude are required." });
    }

    const user = await User.findByIdAndUpdate(userId, { location: { latitude, longitude } }, { new: true });

    if (!user) {
      return res.status(404).json({ message: "User not found." });
    }

    res.json({ message: "Location updated successfully", user });
  } catch (error) {
    res.status(500).json({ message: "Server error", error: error.message });
  }
});

module.exports = router;
