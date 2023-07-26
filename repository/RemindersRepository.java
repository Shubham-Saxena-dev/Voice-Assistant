package com.mbition.hybridcloud.reminders.repository;

import com.mbition.hybridcloud.reminders.models.Reminder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RemindersRepository extends MongoRepository<Reminder, LocalDate> {

    Reminder findByDate(LocalDate date);
}
