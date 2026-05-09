# Meeting Room Booking System

Simple fullstack application for booking and managing meeting rooms.

---

## Features

* Book meeting rooms
* View booking list
* Approval system (PENDING, APPROVED, REJECTED)
* Basic authentication (JWT)
* REST API integration

---

## Tech Stack

Backend:

* Java 21
* Spring Boot
* JWT

Frontend:

* Angular
* TypeScript

Database:

* MySQL

---

## Installation

### 1. Clone Repository

```
git clone https://github.com/your-username/Room-Booking.git
cd MeetingRoom-Project
```

---

### 2. Backend

```
cd serverapp
mvn clean install
mvn spring-boot:run
```

Config database di:

```
src/main/resources/application.yaml
```

---

### 3. Frontend

```
cd clientapp
npm install
ng serve
```

Open:

```
http://localhost:4200
```

---

## API Example

### Create Booking

```
POST /api/bookings
```

Body:

```
{
  "roomName": "Meeting Room A",
  "startTime": "2026-05-03T18:00:00",
  "endTime": "2026-05-03T19:00:00",
  "purpose": "Meeting"
}
```

---

## Status Flow

```
PENDING -> APPROVED
        -> REJECTED
```

---

## Notes

This project is a simple simulation of a meeting room booking system.

---

## Author

Ariel Stevano
