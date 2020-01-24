package com.github.sourcegroove.util.binder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinderTest {
    protected final Log log = LogFactory.getLog(getClass());

    @Test
    public void givenNewBinderWhenAddMultiplePathsThenAdded(){

        Binder binder = new Binder();
        binder.setName("Third");
        binder.add("/ONE/TWO/THREE/FOUR/FirstFolder");
        assertEquals(1, binder.getNodes().size());
        assertEquals(1, binder.getNodes().get(0).getChildren().size());
        binder.add("/ONE/TWO/ALPHA/BRAVO/SecondFolder");
        assertEquals(1, binder.getNodes().size());
        assertEquals(1, binder.getNodes().get(0).getChildren().size());
        assertEquals(2, binder.getNodes().get(0).getChildren().get(0).getChildren().size());
    }

    @Test
    public void givenNewBinderWithNoFoldersWhenAddDeepPathThenAdded(){
        Binder binder = new Binder();
        binder.setName("First");
        assertEquals(0, binder.getNodes().size());
        binder.add("ONE/TWO/THREE/FOUR/FirstFolder");
        assertEquals(1, binder.getNodes().size());

        Binder second = new Binder();
        second.setName("Second");
        second.add("////ONE/TWO/THREE/FOUR/SecondFolder");
        assertEquals(1, second.getNodes().size());

    }

    @Test
    public void givenNewBinderByReportWhenAddDocThenAdded(){

        Binder binder = new Binder();
        binder.add(new BinderDocument("D1"), "DIRECTORYA/2017/01");
        binder.add(new BinderDocument("D2"), "DIRECTORYA/2017/02");
        binder.add(new BinderDocument("D3"), "DIRECTORYA/2017/03");
        binder.add(new BinderDocument("D4"), "DIRECTORYB/2017/01");

        // directories
        BinderNode directoryA = binder.getNodes().get(0);
        BinderNode directoryB = binder.getNodes().get(1);
        assertEquals("DIRECTORYA", directoryA.getName());
        assertEquals("DIRECTORYB", directoryB.getName());

        // years
        BinderNode directoryA2017 = directoryA.getChildren().get(0);
        BinderNode directoryB2017 = directoryB.getChildren().get(0);
        assertEquals("2017", directoryA2017.getName());
        assertEquals("2017", directoryB2017.getName());

        //months
        BinderNode directoryA201701 = directoryA2017.getChildren().get(0);
        assertEquals("01", directoryA201701.getName());
        assertEquals("DIRECTORYA/2017/01", directoryA201701.getPath());

        BinderNode directoryA201702 = directoryA2017.getChildren().get(1);
        assertEquals("02", directoryA201702.getName());
        assertEquals("DIRECTORYA/2017/02", directoryA201702.getPath());

        BinderNode directoryA201703 = directoryA2017.getChildren().get(2);
        assertEquals("03", directoryA201703.getName());
        assertEquals("DIRECTORYA/2017/03", directoryA201703.getPath());


        BinderNode directoryB201701 = directoryB2017.getChildren().get(0);
        assertEquals("01", directoryB201701.getName());
        assertEquals("DIRECTORYB/2017/01", directoryB201701.getPath());

    }

}
