package com.mbition.hybridcloud.reminders.controller;

import com.mbition.hybridcloud.reminders.exceptions.NotFoundException;
import com.mbition.hybridcloud.reminders.models.ReminderRequest;
import com.mbition.hybridcloud.reminders.models.ReminderResponse;
import com.mbition.hybridcloud.reminders.models.VapRequest;
import com.mbition.hybridcloud.reminders.service.RemindersService;
import com.mbition.hybridcloud.reminders.vap.VoiceAssistantTemplate;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reminders/")
@OpenAPIDefinition(info = @Info(title = "MBition Exercise", description = "Reminders Processing", version = "v1.0"))
public class RemindersController implements RemindersApi {


    private static final String FUTURE_REM_PATH = "/futureReminder";
    private static final String DATE_PATH = "date/{date}";
    private static final String GET_BY_LIMIT = "limit/{limit}";
    private static final String VAP_PATH = "/vap";

    private final RemindersService remindersService;
    private final VoiceAssistantTemplate vap;

    @Autowired
    public RemindersController(RemindersService remindersService, VoiceAssistantTemplate vap) {
        this.remindersService = remindersService;
        this.vap = vap;
    }

    @Override
    @Operation(summary = "It is used to create a new reminder")
    @ApiResponse(responseCode = "200", description = "Add a Reminder")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReminderResponse> addReminder(@RequestBody ReminderRequest reminder) {
        var response = remindersService.addReminder(reminder);
        return ResponseEntity.ok(response);
    }

    @Override
    @Operation(summary = "It is used to create a new reminder")
    @ApiResponse(responseCode = "200", description = "Add a Reminder")
    @PostMapping(value = FUTURE_REM_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReminderResponse> addFutureDateReminder(@RequestBody ReminderRequest request) {
        var response = remindersService.addFutureDateReminder(request);
        return ResponseEntity.ok(response);
    }

    @Override
    @Operation(summary = "It is used to get all reminders for a given date")
    @ApiResponse(responseCode = "200", description = "Get all reminders")
    @GetMapping(value = DATE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReminderResponse> getRemindersByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) throws NotFoundException {
        return ResponseEntity.ok(remindersService.getRemindersByDate(date));
    }

    @Override
    @Operation(summary = "It is used to get reminders by limit")
    @ApiResponse(responseCode = "200", description = "Get all reminders")
    @GetMapping(value = GET_BY_LIMIT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReminderResponse>> getReminders(@PathVariable int limit,
                                                               @Value("${pagination.value}") int page) {
        return ResponseEntity.ok(remindersService.getReminders(limit, page));
    }

    @Override
    @Operation(summary = "It is used to delete all reminders.")
    @ApiResponse(responseCode = "200", description = "Delete all reminders")
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReminderResponse> deleteAllReminders() {
        return ResponseEntity.ok(remindersService.deleteAllReminders());
    }

    @Override
    @Operation(summary = "It is used to delete reminders by date.")
    @ApiResponse(responseCode = "200", description = "Delete reminders by date")
    @DeleteMapping(value = DATE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReminderResponse> deleteRemindersByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(remindersService.deleteRemindersByDate(date));
    }

    @Override
    @Operation(summary = "It is used to process based on transcribed text.")
    @ApiResponse(responseCode = "200")
    @PostMapping(value = VAP_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReminderResponse> performVapOperations(@RequestBody VapRequest request) {
        return ResponseEntity.ok(this.vap.processRequest(request));
    }
}
