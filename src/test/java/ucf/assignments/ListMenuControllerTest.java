/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Koby Montenegro
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListMenuControllerTest
{

    @Test
    void AddAnItemTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //New ObservableList
        ObservableList<GetSetItems> testList = FXCollections.observableArrayList();

        //Call AddAnItem
        LMC.AddAnItem(testList, "1991-06-23", "Sonic The Hedgehog");

        //assertEquals to check the description is the same
        assertEquals("Sonic The Hedgehog", testList.get(0).getDescription());
    }


    @Test
    void ClickedDeleteItemTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //New ObservableList
        ObservableList<GetSetItems> testList = FXCollections.observableArrayList();

        //Add to the list
        LMC.AddAnItem(testList, "1991-06-23", "Sonic The Hedgehog");
        LMC.AddAnItem(testList, "1992-11-21", "Miles 'Tails' Prower");
        LMC.AddAnItem(testList, "1994-02-02", "Knuckles The Echidna");

        //Delete the first item
        LMC.DeleteAnItem(testList, testList, 0);

        //Make a boolean variable to use as the actual parameter in asserts.equals
        //See if the second index would equal what was originally at the third index
        boolean actual = testList.get(1).getDescription().equals("Knuckles The Echidna");

        //assertEquals to check that it's been properly updated
        assertEquals(true, actual);
    }


    @Test
    void ClearListTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //New ObservableList
        ObservableList<GetSetItems> testList = FXCollections.observableArrayList();

        //Add item
        LMC.AddAnItem(testList, "1991-06-23", "Sonic The Hedgehog");
        LMC.AddAnItem(testList, "1992-11-21", "Miles 'Tails' Prower");
        LMC.AddAnItem(testList, "1994-02-02", "Knuckles The Echidna");

        //Call ClearList
        LMC.ClearList(testList);

        //Check that the list is empty
        assertEquals(true, testList.isEmpty());
    }


    @Test
    void EditDescriptionTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //New ObservableList
        ObservableList<GetSetItems> testList = FXCollections.observableArrayList();

        //Add item
        LMC.AddAnItem(testList, "1991-06-23", "Sonic The Hedgehog");

        //Call EditDescription
        LMC.EditDescription(testList, testList, 0, "Dr.Eggman");

        //Check that the description changed
        assertEquals("Dr.Eggman", testList.get(0).getDescription());
    }


    @Test
    void EditDueDateTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //New ObservableList
        ObservableList<GetSetItems> testList = FXCollections.observableArrayList();

        //Add Item
        LMC.AddAnItem(testList, "1991-06-23", "Sonic The Hedgehog");

        //Call EditDueDate
        LMC.EditDueDate(testList, testList, 0, "2006-11-14");

        //Check that the due date changed
        assertEquals("2006-11-14", testList.get(0).getDueDate());
    }


    @Test
    void MakeIncompleteTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //New ObservableList
        ObservableList<GetSetItems> testList = FXCollections.observableArrayList();

        //Add items
        LMC.AddAnItem(testList, "1991-06-23", "Sonic The Hedgehog");
        LMC.AddAnItem(testList, "1992-11-21", "Miles 'Tails' Prower");
        LMC.AddAnItem(testList, "1994-02-02", "Knuckles The Echidna");

        //First make the third item complete
        LMC.MakeComplete(testList, testList, 2);

        //Then make it incomplete
        LMC.MakeIncomplete(testList, testList, 2);

        //Make a boolean variable to use as the actual parameter in asserts.equals
        //See if the third index equals incomplete
        boolean actual = testList.get(2).getCompleted().equals("Incomplete");

        assertEquals(true, actual);

    }


    @Test
    void MakeCompleteTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //New ObservableList
        ObservableList<GetSetItems> testList = FXCollections.observableArrayList();

        //Add items
        LMC.AddAnItem(testList, "1991-06-23", "Sonic The Hedgehog");
        LMC.AddAnItem(testList, "1992-11-21", "Miles 'Tails' Prower");
        LMC.AddAnItem(testList, "1994-02-02", "Knuckles The Echidna");

        //First make the third item complete
        LMC.MakeComplete(testList, testList, 2);

        //Make a boolean variable to use as the actual parameter in asserts.equals
        //See if the third index equals incomplete
        boolean actual = testList.get(2).getCompleted().equals("Complete");

        assertEquals(true, actual);
    }


    @Test
    void FileDataFormatting()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //New ObservableList
        ObservableList<GetSetItems> testList = FXCollections.observableArrayList();

        //Add Item
        LMC.AddAnItem(testList, "1991-06-23", "Sonic The Hedgehog");

        //Make Output string to store the data from DataStringFormatting
        String Output = LMC.DataStringFormatting(testList);

        //Call FileDataFormatting
        LMC.FileDataFormatting("Test File", Output);

        //Get pathname
        String testPathName = System.getProperty("user.dir") + "\\\\" + "SavedLists"+ "\\\\" + "Test File" + ".txt";

        //New file object
        File testFile = new File(testPathName);

        //Store if the file exists in a variable to use in assertEquals
        boolean actual = testFile.exists();

        assertEquals(true, actual);
    }


    @Test
    void DataStringFormatting()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //New ObservableList
        ObservableList<GetSetItems> testList = FXCollections.observableArrayList();

        //Add Item
        LMC.AddAnItem(testList, "1991-06-23", "Sonic The Hedgehog");

        //Store result of DataStringFormatting
        String actualString = LMC.DataStringFormatting(testList);

        //Make a new boolean variable to use during assertEquals
        boolean actual = actualString.equals("1991-06-23 Incomplete Sonic The Hedgehog \n");

        assertEquals(true, actual);
    }


    @Test
    void ObtainLastTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //Make a TestString
        String Test1 = "I do not enjoy having to play Big Rigs.";

        //Make a string of arrays
        String[] arrOfStr = Test1.split(" ");

        //Store the result of ObtainLast on the array of Strings in a new String
        String Test2 = LMC.ObtainLast(arrOfStr);

        //Make a variable to check if the last three words match in assertEquals
        boolean actual = Test2.equals("not enjoy having to play Big Rigs. ");

        assertEquals(true, actual);
    }


    @Test
    void LoadAListTest()
    {
        //create a try block and a catch block
        try
        {
            //Create a new variable to call the ListMenuController
            ListMenuController LMC = new ListMenuController();

            //make an observable list
            ObservableList<GetSetItems> testList = FXCollections.observableArrayList();

            //Add items
            LMC.AddAnItem(testList, "1991-06-23", "Sonic The Hedgehog");
            LMC.AddAnItem(testList, "1992-11-21", "Miles 'Tails' Prower");
            LMC.AddAnItem(testList, "1994-02-02", "Knuckles The Echidna");

            //Make Output string to store the data from DataStringFormatting
            String Output = LMC.DataStringFormatting(testList);

            //Call FileDataFormatting using the Output string
            LMC.FileDataFormatting("Test File", Output);

            //Clear the list
            testList.clear();

            //Get pathname
            String LoadPath = System.getProperty("user.dir") + "\\\\" + "SavedLists"+ "\\\\" + "Knuckles File" + ".txt";

            //New FileReader
            FileReader fileToBeRead = new FileReader(LoadPath);

            //Store LoadAList results in testList and check to see the description at
            //at the third index has the original description
            testList = LMC.LoadAList( "Test File", fileToBeRead);

            assertEquals("Knuckles The Echidna", testList.get(2).getDescription());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    void Make100ItemsTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //New ObservableList
        ObservableList<GetSetItems> testList = FXCollections.observableArrayList();

        //Call Make100Items
        LMC.Make100Items(testList);

        //use a boolean variable to test if the item at the first index of data
        //has the description of the item that comes first by due date

        //Make a variable to check that the first index has the description
        //of the first by due date
        boolean actual = testList.get(99).getDueDate().equals("2099-06-11");

        assertEquals(true, actual);
    }


    @Test
    void checkDateTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //Store result of checkDate in a boolean
        boolean actual = LMC.checkDate("1991-06-23");

        assertEquals(true, actual);
    }


    @Test
    void ValidDateTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //Store result of ValidDate in a boolean
        boolean actual = LMC.ValidDate("1991-06-23");

        assertEquals(true, actual);
    }


    @Test
    void ValidDescriptionLengthTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //Store result of ValidDescriptionLength in a boolean
        Boolean actual = LMC.ValidDescriptionLength("Sonic The Hedgehog");

        assertEquals(true, actual);
    }


    @Test
    void addIncompleteItemsListTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //New ObservableLists
        ObservableList<GetSetItems> testList = FXCollections.observableArrayList();
        ObservableList<GetSetItems> incompleteList = FXCollections.observableArrayList();

        //Add Items
        LMC.AddAnItem(testList, "1991-06-23", "Sonic The Hedgehog");
        LMC.AddAnItem(testList, "1992-11-21", "Miles 'Tails' Prower");
        LMC.AddAnItem(testList, "1994-02-02", "Knuckles The Echidna");

        //Make the first item complete
        LMC.MakeComplete(testList, testList, 0);

        //Use addIncompleteItemsList with the incompleteList
        LMC.addIncompleteItemsList(incompleteList, testList);

        //Use a boolean variable to see if incomplete data's second item
        //has a description equal to the first observable list's third item
        boolean actual = incompleteList.get(1).getDescription().equals("Knuckles The Echidna");

        assertEquals(true, actual);
    }


    @Test
    void addCompletedItemsListTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //New ObservableLists
        ObservableList<GetSetItems> testList = FXCollections.observableArrayList();
        ObservableList<GetSetItems> completeList = FXCollections.observableArrayList();

        //Add Items
        LMC.AddAnItem(testList, "1991-06-23", "Sonic The Hedgehog");
        LMC.AddAnItem(testList, "1992-11-21", "Miles 'Tails' Prower");
        LMC.AddAnItem(testList, "1994-02-02", "Knuckles The Echidna");

        //Make the first item complete
        LMC.MakeComplete(testList, testList, 0);

        //Use addCompletedItemsList with the completeList
        LMC.addCompletedItemsList(completeList, testList);

        //Use a boolean variable to store if complete data's first index has the description
        //of the only completed item
        boolean actual = completeList.get(0).getDescription().equals("Sonic The Hedgehog");

        assertEquals(true, actual);
    }


    @Test
    void FindAnotherIndexTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //New ObservableLists
        ObservableList<GetSetItems> testList = FXCollections.observableArrayList();
        ObservableList<GetSetItems> completeList = FXCollections.observableArrayList();

        //Add Items
        LMC.AddAnItem(testList, "1991-06-23", "Sonic The Hedgehog");
        LMC.AddAnItem(testList, "1992-11-21", "Miles 'Tails' Prower");
        LMC.AddAnItem(testList, "1994-02-02", "Knuckles The Echidna");

        //Call MakeComplete
        LMC.MakeComplete(testList, testList, 1);

        //Call addCompletedItemsList
        LMC.addCompletedItemsList(completeList, testList);

        //Store index from FindAnotherIndex
        int ReturnedIndex = LMC.FindAnotherIndex(completeList.get(0), testList);

        assertEquals(1, ReturnedIndex);
    }


    @Test
    void ObtainListTest()
    {
        //Create a new variable to call the ListMenuController
        ListMenuController LMC = new ListMenuController();

        //New ObservableLists
        ObservableList<GetSetItems> testList = FXCollections.observableArrayList();
        ObservableList<GetSetItems> incompleteList = FXCollections.observableArrayList();
        ObservableList<GetSetItems> completeList = FXCollections.observableArrayList();

        //Three Boolean Values
        boolean b1 = true;
        boolean b2 = false;
        boolean b3 = false;

        //Make an ObservableList to store the result of ObtainList
        ObservableList<GetSetItems> listToDisplay = LMC.ObtainList(b1, b2, b3, testList, completeList, incompleteList);

        //Make a new variable to test that listToDisplay is the same as the first
        boolean actual = listToDisplay.equals(testList);

        assertEquals(true, actual);
    }
}