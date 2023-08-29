package com.mbition.hybridcloud.reminders.controller;

import com.mbition.hybridcloud.reminders.exceptions.NotFoundException;
import com.mbition.hybridcloud.reminders.models.ReminderRequest;
import com.mbition.hybridcloud.reminders.models.ReminderResponse;
import com.mbition.hybridcloud.reminders.models.VapRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface RemindersApi {

    ResponseEntity<ReminderResponse> addReminder(ReminderRequest reminder);

    ResponseEntity<ReminderResponse> addFutureDateReminder(ReminderRequest request);

    ResponseEntity<ReminderResponse> getRemindersByDate(LocalDate date) throws NotFoundException;

    ResponseEntity<ReminderResponse> deleteAllReminders();

    ResponseEntity<ReminderResponse> deleteRemindersByDate(LocalDate date);

    ResponseEntity<List<ReminderResponse>> getReminders(int limit, int page);

    ResponseEntity<ReminderResponse> performVapOperations(VapRequest request); //can also be in a different controller

    }
