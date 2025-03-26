import game.Utils.Menu.Menu;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static junit.framework.TestCase.assertTrue;

public class UtilsTest {
    private ByteArrayOutputStream outputStream;

    @Before
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
    }

    @Test
    public void printFormattedMessageTest() {
        TestUtils.setOutputStream(outputStream);
        Menu.printFormattedMessage("ПрИвет");
        assertTrue(TestUtils.logsContains(outputStream, "============\n" + "П Р И В Е Т\n" + "============"));
    }
}