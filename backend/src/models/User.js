const mongoose = require("mongoose");

const userSchema = new mongoose.Schema({
    name: { type: String, required: true, trim: true },
    email: { type: String, required: true, unique: true, lowercase: true },
    password: { type: String, required: true },
    phoneNumber: { type: String, required: true },
    profilePicture: { type: String, default: "" },
    location: {
      latitude: { type: Number, default: null },
      longitude: { type: Number, default: null },
    },
    radius: { type: Number, default: 1 },
    rating: { type: Number, default: 0 },
    totalRatings: { type: Number, default: 0 },
    itemsListed: [{ type: mongoose.Schema.Types.ObjectId, ref: "Item" }],
}, { timestamps: true });

module.exports = mongoose.model("User", userSchema);
