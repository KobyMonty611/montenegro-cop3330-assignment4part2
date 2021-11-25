/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Koby Montenegro
 */

package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetSetItemsTest
{

    @Test
    void getDescription()
    {
        //Make a testItem
        GetSetItems testItem = new GetSetItems("1991-06-23", "Sonic The Hedgehog");

        //Make a boolean variable to use as the actual parameter in assertEquals
        boolean actual = testItem.getDescription().equals("Sonic The Hedgehog");

        assertEquals(true, actual);
    }

    @Test
    void setDescription()
    {
        //Make a testItem
        GetSetItems testItem = new GetSetItems("1991-06-23", "Sonic The Hedgehog");

        //Set the testing Description
        testItem.setDescription("Miles 'Tails' Prower");

        //Make a boolean variable to use as the actual parameter in asserts.equals
        boolean actual = testItem.getDescription().equals("Miles 'Tails' Prower");

        assertEquals(true, actual);
    }


    @Test
    void getDueDate()
    {
        //Make a testItem
        GetSetItems testItem = new GetSetItems("1991-06-23", "Sonic The Hedgehog");

        //Make a boolean variable to use as the actual parameter in asserts.equals
        boolean actual = testItem.getDueDate().equals("1991-06-23");

        assertEquals(true, actual);
    }

    @Test
    void setDueDate()
    {
        //Make a testItem
        GetSetItems testItem = new GetSetItems("1991-06-23", "Sonic The Hedgehog");

        //Set the testing DueDate
        testItem.setDueDate("2017-08-15");

        //Make a boolean variable to use as the actual parameter in asserts.equals
        boolean actual = testItem.getDueDate().equals("2017-08-15");

        assertEquals(true, actual);
    }

    @Test
    void getCompleted()
    {
        //Make a testItem
        GetSetItems testItem = new GetSetItems("1991-06-23", "Sonic The Hedgehog");

        //Make a boolean variable to use as the actual parameter in asserts.equals
        boolean actual = testItem.getCompleted().equals("Incomplete");

        assertEquals(true, actual);
    }

    @Test
    void setCompleted()
    {
        //Make a testItem
        GetSetItems testItem = new GetSetItems("1991-06-23", "Sonic The Hedgehog");

        //Set the testingItem to Complete
        testItem.setCompleted("Complete");

        //Make a boolean variable to use as the actual parameter in asserts.equals
        boolean actual = testItem.getCompleted().equals("Complete");

        assertEquals(true, actual);
    }

}