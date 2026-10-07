# Babble

A social media and messaging app for Android: posts, stories, one-to-one chat and group chat in one place, built on Firebase.

## Features

- **Feed:** create posts, like, comment and use hashtags
- **Stories:** 24-hour stories with a progress-bar viewer
- **Direct messages:** real-time one-to-one chat with media sharing
- **Groups:** create groups, add participants, and set a group name, bio, username and invite link
- **Notifications:** push notifications for messages and activity (FCM)
- **Profiles:** editable name, username, bio, location and link
- **Sign-in:** email and password (with password reset) or phone number with an OTP code

## Tech stack

| Area | Tools |
|---|---|
| Language | Java |
| UI | AndroidX, Material Components, Glide, Picasso, Alerter, StoriesProgressView |
| Backend | Firebase Auth, Realtime Database, Storage, Cloud Messaging |
| Networking | Volley, Gson |
| Monetization | Google Mobile Ads (native templates) |

**Min SDK:** 19 · **Target SDK:** 34

## Project structure

```
app/src/main/java/com/mansibyasir/officialchat/
├── authEmail/       # Email sign-up, sign-in, password reset
├── authPhone/       # Phone OTP flow
├── adapter/         # Feed, chat, story, group and user adapters
├── groups/          # Group creation, chat, profile and sharing
├── groupSettings/   # Group name, bio, username and link editors
├── settings/        # Profile field editors
└── notifications/   # Notification screen
nativetemplates/     # AdMob native ad templates module
```

## Getting started

1. Clone the repo and open it in **Android Studio**.
2. Create a Firebase project, register the package `com.mansibyasir.officialchat`, and put your own `google-services.json` in `app/`.
3. Enable **Email/Password** and **Phone** authentication, **Realtime Database** and **Storage**.
4. Build and run.

## Author

**Mansib Yasir** · [GitHub](https://github.com/MansibR360)
