package timeplan.client.views.editshift;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EditShiftViewModelTest
{
  String startTimeEmp;
  String endTimeEmp;
  private final EditShiftViewModel editShiftViewModel = new EditShiftViewModel();

  @Test void validateTimeBeforeEndtime()
  {
    startTimeEmp = "12:30";
    endTimeEmp = "11:30";

    assertFalse(editShiftViewModel.validateTime(startTimeEmp, endTimeEmp));
  }

  @Test void validateTimeLet()
  {
    startTimeEmp = "a2:d0";
    endTimeEmp = "11:30";

    assertFalse(editShiftViewModel.validateTime(startTimeEmp, endTimeEmp));
  }

  @Test void validateSplit()
  {
    startTimeEmp = "12-20";
    endTimeEmp = "14:30";

    assertFalse(editShiftViewModel.validateTime(startTimeEmp, endTimeEmp));
  }

  @Test void validateSpecial()
  {
    startTimeEmp = "1!:20";
    endTimeEmp = "14:30";

    assertFalse(editShiftViewModel.validateTime(startTimeEmp, endTimeEmp));
  }

  @Test void validateTimeAfter0000()
  {
    startTimeEmp = "22:00";
    endTimeEmp = "01:00";

    assertFalse(editShiftViewModel.validateTime(startTimeEmp, endTimeEmp));
  }

  @Test void validateTimetrue() {
    startTimeEmp = "10:00";
    endTimeEmp = "16:00";

    assertTrue(editShiftViewModel.validateTime(startTimeEmp, endTimeEmp));
  }

}