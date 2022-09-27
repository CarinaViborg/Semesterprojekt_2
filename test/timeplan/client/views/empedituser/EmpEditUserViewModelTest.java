package timeplan.client.views.empedituser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmpEditUserViewModelTest
{
  private final EmpEditUserViewModel viewModel = new EmpEditUserViewModel();
  private String string;

  @BeforeEach
  public void clearString(){
    string = "";
  }

  @Test
  void phoneValiOutOfRangeToHigh() {
    string = "123456789";
    assertFalse(viewModel.phoneVali(string));
  }

  @Test
  void phoneValiOutOfRangeToLow() {
    string = "1234567";
    assertFalse(viewModel.phoneVali(string));
  }

  @Test
  void phoneValiLetters() {
    string = "dfgghjkl";
    assertFalse(viewModel.phoneVali(string));
  }

  @Test
  void phoneValiLettersAndNum() {
    string = "22kler89";
    assertFalse(viewModel.phoneVali(string));
  }

  @Test
  void phoneValiSpecial() {
    string = "22!*/563";
    assertFalse(viewModel.phoneVali(string));
  }

  @Test
  void phoneVali() {
    string = "12345678";
    assertTrue(viewModel.phoneVali(string));
  }

  @Test
  void emailValidWideSpace1stPart() {
    string =" 299141@via.dk";
    assertFalse(viewModel.emailValid(string));
  }

  @Test
  void emailValidWideSpace2ndPart() {
    string ="299141@ via.dk";
    assertFalse(viewModel.emailValid(string));
  }

  @Test
  void emailValidWideSpace3edPart() {
    string ="299141@via. dk";
    assertFalse(viewModel.emailValid(string));
  }

  @Test
  void emailValiNoSnabela() {
    string ="299141via.dk";
    assertFalse(viewModel.emailValid(string));
  }

  @Test
  void emailValiNoDot() {
    string ="299141@viadk";
    assertFalse(viewModel.emailValid(string));
  }

  @Test
  void emailValiOperatorIn2ndPart() {
    string ="299141@vi!.dk";
    assertFalse(viewModel.emailValid(string));
  }

  @Test
  void emailValiOperatorIn3edPart() {
    string ="299141@via.!k";
    assertFalse(viewModel.emailValid(string));
  }

  @Test
  void emailValiNumberIn2ndPart() {
    string ="299141@vi1.dk";
    assertFalse(viewModel.emailValid(string));
  }

  @Test
  void emailValiNumberIn3edPart() {
    string ="299141@via.1k";
    assertFalse(viewModel.emailValid(string));
  }

  @Test
  void emailVali() {
    string = "299141@via.dk";
    assertTrue(viewModel.emailValid(string));
  }
}