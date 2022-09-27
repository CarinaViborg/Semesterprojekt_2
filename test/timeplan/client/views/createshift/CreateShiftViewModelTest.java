package timeplan.client.views.createshift;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateShiftViewModelTest
{
  String startTime;
  String endTime;
  private final CreateShiftViewModel createShiftViewModel = new CreateShiftViewModel();

  @Test void validateTimeBeforeEndtime()
  {
    startTime = "12:30";
    endTime = "11:30";

    assertFalse(createShiftViewModel.validateTime(startTime,endTime));
  }

  @Test void validateTimeLet()
  {
    startTime = "a2:d0";
    endTime = "11:30";

    assertFalse(createShiftViewModel.validateTime(startTime,endTime));
  }

  @Test void validateSplit()
  {
    startTime = "12-20";
    endTime = "14:30";

    assertFalse(createShiftViewModel.validateTime(startTime,endTime));
  }

  @Test void validateSpecial()
  {
    startTime = "1!:20";
    endTime = "14:30";

    assertFalse(createShiftViewModel.validateTime(startTime,endTime));
  }

  @Test void validateTimeAfter0000()
  {
    startTime = "22:00";
    endTime = "01:00";

    assertFalse(createShiftViewModel.validateTime(startTime,endTime));
  }

  @Test void validateTimetrue() {
    startTime = "10:00";
    endTime = "16:00";

    assertTrue(createShiftViewModel.validateTime(startTime, endTime));
  }

  @Test void dateValidationBeforeToday()
  {
    LocalDate date = LocalDate.of(2022, 5, 22);

    assertFalse(createShiftViewModel.dateValidation(date));
  }

  @Test void dateValidationAfterToday()
  {
    LocalDate date = LocalDate.of(2023, 5, 26);

    assertTrue(createShiftViewModel.dateValidation(date));
  }


}