package timeplan.client.views.adminedituser;

import org.junit.jupiter.api.BeforeEach;
import timeplan.client.core.ViewModelFactory;

import static org.junit.jupiter.api.Assertions.*;

class AdminEditUserViewModelTest
{

    String string;
    AdminEditUserViewModel viewModel = ViewModelFactory.getViewModelFactory()
        .getEditUserViewModel();

    @BeforeEach
    public void clearString(){
        string = "";
    }

    @org.junit.jupiter.api.Test
    void nameValiOperator () {
        string = "=?!";
        assertFalse(viewModel.validateFornavn(string));
    }
    @org.junit.jupiter.api.Test
    void nameValiNumbers() {
        string = "123";
        assertFalse(viewModel.validateFornavn(string));
    }
    @org.junit.jupiter.api.Test
    void nameValiNumbersAndLetters() {
        string = "Jes123";
        assertFalse(viewModel.validateFornavn(string));
    }
    @org.junit.jupiter.api.Test
    void nameValiname() {
        string = "Jens";

        assertTrue(viewModel.validateFornavn(string));
    }

    @org.junit.jupiter.api.Test
    void lnameValiWideSpace() {
        string = "Jesn ";

        assertFalse(viewModel.validateEfternavn(string));
    }
    @org.junit.jupiter.api.Test
    void lnameValiOperator () {
        string = "=?!";

        assertFalse(viewModel.validateEfternavn(string));
    }
    @org.junit.jupiter.api.Test
    void lnameValiNumbers() {
        string = "123";
        assertFalse(viewModel.validateEfternavn(string));
    }
    @org.junit.jupiter.api.Test
    void lnameValiNumbersAndLetters() {
        string = "Jes123";
        assertFalse(viewModel.validateEfternavn(string));
    }
    @org.junit.jupiter.api.Test
    void lnameValiname() {
        string = "Jens";

        assertTrue(viewModel.validateEfternavn(string));
    }


    @org.junit.jupiter.api.Test
    void phoneValiOutOfRangeToHigh() {
        string = "123456789";

        assertFalse(viewModel.phoneVali(string));
    }
    @org.junit.jupiter.api.Test
    void phoneValiOutOfRangeToLow() {
        string = "1234567";

        assertFalse(viewModel.phoneVali(string));
    }
    @org.junit.jupiter.api.Test
    void phoneValiLetters() {
        string = "asdfghjk";

        assertFalse(viewModel.phoneVali(string));
    }
    @org.junit.jupiter.api.Test
    void phoneValiLettersAndNum() {
        string = "22kler89";

        assertFalse(viewModel.phoneVali(string));
    }
    @org.junit.jupiter.api.Test
    void phoneValiSpecial() {
        string = "22!*/563";

        assertFalse(viewModel.phoneVali(string));
    }
    @org.junit.jupiter.api.Test
    void phoneVali() {
        string = "12345678";

        assertTrue(viewModel.phoneVali(string));
    }

    @org.junit.jupiter.api.Test
    void emailValidWideSpace1stPart() {
        string =" 299141@via.dk";

        assertFalse(viewModel.validateMail(string));

    }

    @org.junit.jupiter.api.Test
    void emailValidWideSpace2ndPart() {
        string ="299141@ via.dk";

        assertFalse(viewModel.validateMail(string));


    }
    @org.junit.jupiter.api.Test
    void emailValidWideSpace3edPart() {
        string ="299141@via. dk";

        assertFalse(viewModel.validateMail(string));


    }
    @org.junit.jupiter.api.Test
    void emailValiNoSnabela() {
        string ="299141via.dk";

        assertFalse(viewModel.validateMail(string));

    }
    @org.junit.jupiter.api.Test
    void emailValiNoDot() {
        string ="299141@viadk";

        assertFalse(viewModel.validateMail(string));


    }
    @org.junit.jupiter.api.Test
    void emailValiOperatorIn2ndPart() {
        string ="299141@vi!.dk";

        assertFalse(viewModel.validateMail(string));


    }
    @org.junit.jupiter.api.Test
    void emailValiOperatorIn3edPart() {
        string ="299141@via.!k";

        assertFalse(viewModel.validateMail(string));


    }

    @org.junit.jupiter.api.Test
    void emailValiNumberIn2ndPart() {
        string ="299141@vi1.dk";

        assertFalse(viewModel.validateMail(string));


    }

    @org.junit.jupiter.api.Test
    void emailValiNumberIn3edPart() {
        string ="299141@via.1k";

        assertFalse(viewModel.validateMail(string));


    }

    @org.junit.jupiter.api.Test
    void emailVali() {
        string ="299141@via.dk";

        assertTrue(viewModel.validateMail(string));
    }
}