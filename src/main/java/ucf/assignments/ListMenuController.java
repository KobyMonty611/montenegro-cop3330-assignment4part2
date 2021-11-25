/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Koby Montenegro
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ListMenuController implements Initializable
{
    //Use to represent which lists should be shown to the user
    boolean booleanShowAll = true;
    boolean booleanShowIncomplete = false;
    boolean booleanShowComplete = false;

    //FXML variables imported from the ListManager.fxml File
    @FXML
    public TableView ViewTheTable;
    public TableColumn DescriptionTableColumn;
    public TableColumn DueDateTableColumn;
    public TableColumn CompletedTableColumn;

    //FXML variables imported from the ListManager.fxml File
    @FXML
    public TextField AddDescriptionText;
    public TextField AddDueDateText;
    public TextField EditDescriptionText;
    public TextField EditDueDateText;
    public TextField LoadListTextField;
    public TextField SaveListTextField;

    //make three observable lists for each type of dataList to be shown to the user
    ObservableList<GetSetItems> dataList = FXCollections.observableArrayList();
    ObservableList<GetSetItems> incompleteList = FXCollections.observableArrayList();
    ObservableList<GetSetItems> completeList = FXCollections.observableArrayList();


    //MAIN FUNCTIONS
    @FXML
    public void initialize(URL url, ResourceBundle rb)
    {
        //set each column to a new value using .setCellValueFactory
        DescriptionTableColumn.setCellValueFactory(new PropertyValueFactory("Description"));
        DueDateTableColumn.setCellValueFactory(new PropertyValueFactory("DueDate"));
        CompletedTableColumn.setCellValueFactory(new PropertyValueFactory("Completed"));
    }

    //ADDING ITEMS
    public void AddAnItem(ObservableList<GetSetItems> list, String NewDueDate, String NewDescription)
    {
        //Use GetSetItems constructor to make a new item
        GetSetItems addingItem = new GetSetItems(NewDueDate, NewDescription);
        //Add the item to list
        list.add(addingItem);
    }

    @FXML
    public void ClickedAddItem(ActionEvent actionEvent)
    {
        //Get text from AddDueDateText and AddDescriptionText textfields
        String NewDueDate = AddDueDateText.getText();
        String NewDescription = AddDescriptionText.getText();

        //Make sure date and description are valid
        if(ValidDate(NewDueDate) && ValidDescriptionLength(NewDescription))
        {
            //Use AddItem
            AddAnItem(dataList, NewDueDate, NewDescription);

            //Update the complete and incomplete list
            addCompletedItemsList(completeList, dataList);
            addIncompleteItemsList(incompleteList, dataList);

            //Get list using obtainList
            ObservableList<GetSetItems> displayList = ObtainList(booleanShowAll, booleanShowComplete, booleanShowIncomplete, dataList, completeList, incompleteList);

            //Set the view to completeList
            ViewTheTable.getItems().setAll(displayList);

        }
    }


    //DELETING ITEMS
    public void DeleteAnItem(ObservableList<GetSetItems> list, ObservableList<GetSetItems> list2, int index)
    {
        //Remove list if both equal each other
        if(list.equals(list2))
        {
            list.remove(index);
        }
        else
        {
            //Use FindAnotherIndex and store the result
            int deleteIndex = FindAnotherIndex(list2.get(index), list);

            //Use the index to remove the item from the second list
            list2.remove(index);
            //Use the deleteIndex to remove the item from the first list
            list.remove(deleteIndex);
        }

    }

    @FXML
    public void ClickedDeleteItem(ActionEvent actionEvent)
    {
        //Get the index of the item being clicked on
        int thingToDelete = ViewTheTable.getSelectionModel().getSelectedIndex();

        //Use ObtainList to get listToUse
        ObservableList<GetSetItems> listToUse = ObtainList(booleanShowAll, booleanShowComplete, booleanShowIncomplete, dataList, completeList, incompleteList);
        DeleteAnItem(dataList, listToUse, thingToDelete);

        ViewTheTable.getItems().setAll(listToUse);
    }


    //CLEARING LISTS
    public  void ClearList(ObservableList listToBeCleared)
    {
        listToBeCleared.clear();
    }

    @FXML
    public void ClickedClearList(ActionEvent actionEvent)
    {
        //Use ClearList to cleat out dataList
        ClearList(dataList);
        ViewTheTable.getItems().setAll(dataList);
    }


    //EDITING DESCRIPTION
    public void EditDescription(ObservableList<GetSetItems> list, ObservableList<GetSetItems> list2, int index, String NewDescription)
    {
        //Set description of item at the index if they are both the same
        if(list.equals(list2))
        {
            list.get(index).setDescription(NewDescription);
        }
        else
        {
            //Use FindAnotherIndex and store the result
            int editIndex = FindAnotherIndex(list2.get(index), list);


            //Use the index to edit the description in the second list
            list2.get(index).setDescription(NewDescription);
            //Use the editIndex to edit the description in the first list
            list.get(editIndex).setDescription(NewDescription);
        }

    }

    @FXML
    public void ClickedEditDescription(ActionEvent actionEvent)
    {
        //Get text from EditDescriptionText text field
        String descriptionToBeEdited = EditDescriptionText.getText();
        if(ValidDescriptionLength(descriptionToBeEdited))
        {
            //Get index to edit
            int editIndex = ViewTheTable.getSelectionModel().getSelectedIndex();
            //Use obtainList to get listToUse
            ObservableList<GetSetItems> listToUse = ObtainList(booleanShowAll, booleanShowComplete, booleanShowIncomplete, dataList, completeList, incompleteList);

            //Change the description in both lists
            EditDescription(dataList, listToUse, editIndex, descriptionToBeEdited);
            //Display the list
            ViewTheTable.getItems().setAll(listToUse);
        }
    }


    //EDITING DUEDATE
    public void EditDueDate(ObservableList<GetSetItems> list, ObservableList<GetSetItems> list2, int index, String NewDueDate)
    {
        //Set the NewDueDate at the index
        if(list.equals(list2))
        {
            list.get(index).setDueDate(NewDueDate);
        }
        else
        {
            //Use FindAnotherIndex and store the result
            int EditIndex = FindAnotherIndex(list2.get(index), list);

            //Use the index to edit the due date in the second list
            list2.get(index).setDueDate(NewDueDate);
            //Use the index to edit the description in the first list
            list.get(EditIndex).setDueDate(NewDueDate);
        }

    }

    @FXML
    public void ClickedEditDueDate(ActionEvent actionEvent)
    {
        //Get text from EditDueDateText text field
        String NewDueDate = EditDueDateText.getText();

        if(ValidDate(NewDueDate))
        {
            //Get Index to edit
            int editIndex = ViewTheTable.getSelectionModel().getSelectedIndex();

            //Use ObtainList to get ListToUse
            ObservableList<GetSetItems> listToUse = ObtainList(booleanShowAll, booleanShowComplete, booleanShowIncomplete, dataList, completeList, incompleteList);

            //Change the dueDate in both lists
            EditDueDate(dataList, listToUse, editIndex, NewDueDate);
            //Display the list
            ViewTheTable.getItems().setAll(listToUse);

        }
    }


    //MAKING AN ITEM INCOMPLETE
    public void MakeIncomplete(ObservableList<GetSetItems> list, ObservableList<GetSetItems> list2, int index)
    {
        //Make item incomplete if both lists are the same
        if(list.equals(list2))
        {
            list.get(index).setCompleted("Incomplete");
        }
        else
        {
            //Set completed to Incomplete
            list2.get(index).setCompleted("Incomplete");

            //Use FindAnotherIndex and store the result
            int editIndex = FindAnotherIndex( list2.get(index), list);
            //Set the item to incomplete at the index
            list.get(editIndex).setCompleted("Incomplete");
        }
    }

    @FXML
    public void ClickedMakeItemIncomplete(ActionEvent actionEvent)
    {
        //Get index to edit
        int thingToEdit= ViewTheTable.getSelectionModel().getSelectedIndex();

        //Use ObtainList to get listToUse
        ObservableList<GetSetItems> listToUse = ObtainList(booleanShowAll, booleanShowComplete, booleanShowIncomplete, dataList, completeList, incompleteList);
        //Display List
        ViewTheTable.getItems().setAll(listToUse);

        //Use MakeIncomplete to make the item incomplete in both lists
        MakeIncomplete(dataList, listToUse, thingToEdit);

        //Use add incomplete and complete items list to update the lists
        addIncompleteItemsList(incompleteList, dataList);
        addCompletedItemsList(completeList, dataList);

        //Display List
        ViewTheTable.getItems().setAll(listToUse);
    }


    //MAKING AN ITEM COMPLETE
    public void MakeComplete(ObservableList<GetSetItems> list, ObservableList<GetSetItems> list2, int index)
    {
        //Make item complete if both lists are equal
        if(list.equals(list2))
        {
            list.get(index).setCompleted("Complete");
        }
        else
        {
            //Set completed to Complete
            list2.get(index).setCompleted("Complete");

            //Use FindAnotherIndex and store the result
            int editIndex = FindAnotherIndex( list2.get(index), list );
            //Set the item to Complete at the index
            list.get(editIndex).setCompleted("Complete");
        }
    }

    @FXML
    public void ClickedMakeItemComplete(ActionEvent actionEvent)
    {

        //Get index to use
        int thingToEdit= ViewTheTable.getSelectionModel().getSelectedIndex();

        //Use ObtainList to get listToUse
        ObservableList<GetSetItems> listToUse = ObtainList(booleanShowAll, booleanShowComplete, booleanShowIncomplete, dataList, completeList, incompleteList);
        //Display the list
        ViewTheTable.getItems().setAll(listToUse);

        //Use MakeComplete to make the item complete in both lists
        MakeComplete(dataList, listToUse, thingToEdit);

        //Use add incomplete and complete items list to update the lists
        addIncompleteItemsList(incompleteList, dataList);
        addCompletedItemsList(completeList, dataList);

        //Display the list
        ViewTheTable.getItems().setAll(listToUse);

    }

    //SHOW ALL, INCOMPLETE, OR COMPLETE ITEMS
    @FXML
    public void ClickedShowAll(ActionEvent actionEvent)
    {
        //Make booleanShowAll true and the other two false so they don't show up
        booleanShowAll = true;
        booleanShowComplete = false;
        booleanShowIncomplete = false;

        //Call listToDisplay
        ObservableList<GetSetItems> listToDisplay = ObtainList(booleanShowAll, booleanShowComplete, booleanShowIncomplete, dataList, completeList, incompleteList);

        //Set the table view to the dataList
        ViewTheTable.getItems().setAll(listToDisplay);
    }

    @FXML
    public void ClickedShowIncomplete(ActionEvent actionEvent)
    {
        //Use addIncompleteItemsList to update the incompleteList
        addIncompleteItemsList(incompleteList, dataList);

        //Make booleanShowIncomplete true and the other two false so they don't show up
        booleanShowAll = false;
        booleanShowIncomplete = true;
        booleanShowComplete = false;

        //Set the table view to the incompleteList
        ViewTheTable.getItems().setAll(incompleteList);
    }

    @FXML
    public void ClickedShowComplete(ActionEvent actionEvent)
    {
        //Use addCompleteItemsList to update the completeList
        addCompletedItemsList(completeList, dataList);

        //Make booleanShowComplete true and the other two false so they don't show up
        booleanShowAll = false;
        booleanShowIncomplete = false;
        booleanShowComplete = true;

        //Set the table view to completeList
        ViewTheTable.getItems().setAll(completeList);
    }


    //IN RELATION TO SAVING DATA
    public void FileDataFormatting(String UserFileName, String textToOutput)
    {
        //Get users directory to make the pathname and overwrite the previous file
        String newPath = System.getProperty("user.dir") + "\\\\" + "SavedLists"+ "\\\\" + UserFileName + ".txt";

        //Use pathname to create a new file object
        File fileBeingFormatted = new File(newPath);

        try {
            fileBeingFormatted.createNewFile();
            FileWriter myWriter = new FileWriter(newPath);
            myWriter.write(textToOutput);
            myWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String DataStringFormatting(ObservableList<GetSetItems> datalist)
    {
        String Output = "";

        for(int i = 0; i < datalist.size(); i++)
        {
            //Make a temp string, add each attribute, add a newline at the end of each one
            String Temp = datalist.get(i).getDueDate() + " " + datalist.get(i).getCompleted() + " " + datalist.get(i).getDescription() + " \n";

            //Add to output after each one
            Output += Temp;
        }

        return Output;
    }

    @FXML
    public void ClickedSaveList(ActionEvent actionEvent)
    {
        //Get users directory, make new directory called SavedLists
        String Pathname = System.getProperty("user.dir") + "\\\\" + "SavedLists";
        File SaveFile = new File(Pathname);
        SaveFile.mkdirs();

        //Get the name of the list to save
        String SavingName = SaveListTextField.getText();

        //Use DataStringFormatting to make dataList into a string
        String StringToWrite = DataStringFormatting(dataList);

        //Use FileDataFormatting to save the new list in a .txt file
        FileDataFormatting(SavingName, StringToWrite);

    }


    //IN RELATION TO LOADING DATA
    public  String ObtainLast(String [] arrOfStr)
    {
        String Output = "";

        //Start at third index
        for(int i = 2; i < arrOfStr.length; i++)
        {
            //Add a space after each string
            Output += arrOfStr[i] + " ";
        }

        return Output;
    }

    public ObservableList<GetSetItems> LoadAList(String ListNameToLoad, FileReader fileToBeRead)
    {
        Scanner sc = new Scanner(fileToBeRead);

        //Temp list used to to store the read dataList
        ObservableList<GetSetItems> templist = FXCollections.observableArrayList();

        while(sc.hasNextLine())
        {
            //Make each line a string
            String ItemString = sc.nextLine();

            //Split by spaces
            String[] arrOfStr = ItemString.split(" ");

            //make a new item and set it's due date to the string array's first item
            //set the strings description to every thing past the second item of the string array using
            //the auxillary function ObtainLast

            //New item, set due date to the first item in the arrOfStr
            //Use
            GetSetItems tempitem = new GetSetItems(arrOfStr[0], ObtainLast(arrOfStr));

            //If the second index is equal, make it complete, if not make it incomplete
            if(arrOfStr[1].equals("Complete"))
            {
                tempitem.setCompleted("Complete");
            }
            else
            {
                tempitem.setCompleted("Incomplete");
            }

            //Add new item to the list
            templist.add(tempitem);
        }
        
        return templist;
    }

    @FXML
    public void ClickedLoadList(ActionEvent actionEvent) 
    {
        //Get name of list to load
        String LoadingName = LoadListTextField.getText();
        
        //Make pathname
        String LoadingPath = System.getProperty("user.dir") + "\\\\" + "SavedLists"+ "\\\\" + LoadingName + ".txt";
        
        //Make file object using the pathname
        File loadingFile = new File(LoadingPath);
        
        //If that file exists
        if(loadingFile.exists())
        {
            try 
            {
                //File reader to read from the file
                FileReader fileToBeRead = new FileReader(LoadingPath);

                //set the dataList list to the list returned by the LoadAList function
                //Set dataList to the list from LoadAList
                dataList = LoadAList(LoadingName, fileToBeRead);

                //Set the table view to the new list
                ViewTheTable.getItems().setAll(dataList);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    //END OF MAIN FUNCTIONS

    //TO CHECK THAT THE PROGRAM CAN HANDLE 100 LISTS
    public static void  Make100Items(ObservableList<GetSetItems> HundredOfTheList)
    {
        //Start with 2000 for the year of the due date
        for(int i = 0; i < 100; i++)
        {
            //For 2000-2009
            if(i <= 9)
            {
                //New DueDate string
                String NewDueDate = "200"+ i +"-06-11";

                //Use string to create a new item
                GetSetItems tempitem = new GetSetItems(NewDueDate,"I'm Number " + i + "!");

                //Add to the list
                HundredOfTheList.add(tempitem);
            }
            //For 2010-2099
            else if((9 < i) && (i <= 100))
            {
                //New DueDate String
                String NewDueDate = "20"+ i +"-06-11";

                //Use string to create a new item
                GetSetItems tempitem = new GetSetItems(NewDueDate, "I'm Number " + i + "!");

                //Add to the list
                HundredOfTheList.add(tempitem);
            }
        }
    }

    @FXML
    public void Make100ItemsClicked(ActionEvent actionEvent)
    {
        Make100Items(dataList);

        //Display the list
        ViewTheTable.getItems().setAll(dataList);
    }

    //USED TO MAKE SURE THE DATE IS VALID
    public boolean checkDate(String date)
    {
        //Check the date format matches 'yyyy-mm-dd'
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    public boolean ValidDate(String date)
    {
        boolean status = false;

        //Check the dateFormat matches 'yyyy-mm-dd'
        if (checkDate(date))
        {
            //Make new DateFormat with "yyyy-mm-dd"
            DateFormat Gregorian = new SimpleDateFormat("yyyy-MM-dd");

            //Make setLenient false, makes it so date must be valid in Gregorian format
            Gregorian.setLenient(false);


            try
            {
                //Set true if date format can be parsed
                Gregorian.parse(date);
                status = true;
            }
            catch (Exception e)
            {
                status = false;
            }
        }
        return status;
    }

    //USED TO MAKE SURE DESCRIPTION LENGTH IS VALID
    public  boolean ValidDescriptionLength(String description)
    {
        //Check if description is between 1 and 256 characters
        if(description.length() >= 1 && description.length() <= 256)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //USED TO CHECK WHEN COMPLETE OR INCOMPLETE ITEMS ARE NEEDED
    public void addCompletedItemsList(ObservableList<GetSetItems> list1, ObservableList<GetSetItems> list2)
    {
        //Clear List to add the Complete items to
        list1.clear();

        for(int i = 0; i < list2.size(); i++)
        {
            //Add if item is complete
            if( list2.get(i).getCompleted().equals("Complete"))
            {
                list1.add(list2.get(i));
            }
        }

    }

    public void addIncompleteItemsList(ObservableList<GetSetItems> list1, ObservableList<GetSetItems> list2)
    {
        //Clear List to add the Incomplete items to
        list1.clear();

        for(int i = 0; i < list2.size(); i++)
        {
            //Add item if incomplete
            if( list2.get(i).getCompleted().equals("Incomplete"))
            {
                list1.add(list2.get(i));
            }
        }

    }

    //USED TO FIND THE INDEX OF AN ITEM IN ANOTHER LIST
    public int FindAnotherIndex(GetSetItems itemToFind, ObservableList <GetSetItems> data1)
    {
        //Use for loop to look through a list
        for(int i = 0; i < data1.size(); i++)
        {
            //Return index if found
            if(data1.get(i).equals(itemToFind))
            {
                return i;
            }
        }

        //If not return -1
        return  -1;
    }

    public static ObservableList<GetSetItems> ObtainList(boolean b1, boolean b2, boolean b3, ObservableList<GetSetItems> list1, ObservableList<GetSetItems> list2, ObservableList<GetSetItems> list3)
    {
        //If b1 is true
        if(b1)
        {
            return list1;
        }
        //If b2 is true
        else if(b2)
        {
            return list2;
        }
        //If b3 is true
        else
        {
            return list3;
        }
    }
}
