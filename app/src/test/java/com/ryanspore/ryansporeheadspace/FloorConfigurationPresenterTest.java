package com.ryanspore.ryansporeheadspace;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FloorConfigurationPresenterTest {

    private TestViewDelegate testViewDelegate;
    private FloorConfigurationPresenter subject;
    private TestCompletionDelegate testCompletionDelegate;

    @Before
    public void setUp() throws Exception {
        testViewDelegate = new TestViewDelegate();
        testCompletionDelegate = new TestCompletionDelegate();
        subject = new FloorConfigurationPresenter(testViewDelegate);
        subject.setFloorConfigurationDelegate(testCompletionDelegate);
    }

    @Test
    public void onSubmitClicked_informsDelegateOfFloorCount() throws Exception {
        subject.onSubmitClicked("5");
        assertEquals(5, testCompletionDelegate.floorCount.intValue());
    }

    @Test
    public void onFloorCountChanged_withEmptyInput_disablesSubmitButton() throws Exception {
        subject.onFloorCountChanged("");
        assertEquals(false, testViewDelegate.enabled);
    }

    @Test
    public void onFloorCountChanged_withNonNumericInput_disablesSubmitButton() throws Exception {
        subject.onFloorCountChanged("cat");
        assertEquals(false, testViewDelegate.enabled);
    }

    @Test
    public void onFloorCountChanged_withNumericInput_enablesSubmitButton() throws Exception {
        subject.onFloorCountChanged("12");
        assertEquals(true, testViewDelegate.enabled);
    }

    private static class TestViewDelegate implements FloorConfigurationPresenter.Delegate {
        Boolean enabled = null;

        @Override
        public void setSubmitButtonEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    private static class TestCompletionDelegate implements FloorConfigurationView.Delegate {
        Integer floorCount = null;

        @Override
        public void floorConfigurationComplete(int floorCount) {
            this.floorCount = floorCount;
        }
    }
}