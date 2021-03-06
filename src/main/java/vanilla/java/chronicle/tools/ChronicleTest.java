package vanilla.java.chronicle.tools;

import vanilla.java.chronicle.Excerpt;

import java.io.File;

/**
 * @author plawrey
 */
public enum ChronicleTest {
    ;

    /**
     * Delete a chronicle on exit, for testing
     *
     * @param basePath of the chronicle
     */
    public static void deleteOnExit(String basePath) {
        new File(basePath + ".data").deleteOnExit();
        new File(basePath + ".index").deleteOnExit();
    }

    /**
     * Take a text copy of the contents of the Excerpt without changing it's position. Can be called in the debugger.
     *
     * @param excerpt to get text from
     * @return 256 bytes as text with `.` replacing special bytes.
     */
    public static String asString(Excerpt excerpt) {
        return asString(excerpt, excerpt.position());
    }

    /**
     * Take a text copy of the contents of the Excerpt without changing it's position. Can be called in the debugger.
     *
     * @param excerpt  to get text from
     * @param position the position to get text from
     * @return up to 1024 bytes as text with `.` replacing special bytes.
     */
    public static String asString(Excerpt excerpt, int position) {
        return asString(excerpt, position, 1024);
    }

    /**
     * Take a text copy of the contents of the Excerpt without changing it's position. Can be called in the debugger.
     *
     * @param excerpt  to get text from
     * @param position the position to get text from
     * @param length   the maximum length
     * @return length bytes as text with `.` replacing special bytes.
     */
    public static String asString(Excerpt excerpt, int position, int length) {
        int limit = Math.min(position + length, excerpt.capacity());
        StringBuilder sb = new StringBuilder(limit - position);
        for (int i = position; i < limit; i++) {
            char ch = (char) excerpt.readUnsignedByte(i);
            if (ch < ' ' || ch > 127) ch = '.';
            sb.append(ch);
        }
        return sb.toString();
    }
}
