/*
 * Copyright 2011 Peter Lawrey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vanilla.java.chronicle;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * An extracted record within a Chronicle.  This record refers to one entry.
 *
 * @author peter.lawrey
 */
public interface Excerpt<C extends Chronicle> extends RandomDataInput, RandomDataOutput, ByteStringAppender, ByteStringParser {
    /**
     * @return the chronicle this is an excerpt for.
     */
    C chronicle();

    /**
     * Attempt to set the index to the next index.  The method is re-tryable as another thread or process could be writing to this Chronicle.
     *
     * @return true if the index could be set to a valid entry.
     */
    boolean nextIndex();

    /**
     * Attempt to set the index to this number.  The method is re-tryable as another thread or process could be writing to this Chronicle.
     *
     * @param index within the Chronicle
     * @return true if the index could be set to a valid entry.
     * @throws IndexOutOfBoundsException If index < 0
     */
    boolean index(long index) throws IndexOutOfBoundsException;

    /**
     * @return the index of a valid entry or -1 if the index has never been set.
     */
    long index();

    /**
     * Set the position within this except.
     *
     * @param position to move to.
     * @return this
     */
    Excerpt<C> position(int position);

    /**
     * @return the position within this excerpt
     */
    int position();

    /**
     * @return the capacity of the excerpt.
     */
    int capacity();

    /**
     * @return the number fo bytes unread.
     */
    int remaining();

    /**
     * @return The byte order of this Excerpt
     */
    ByteOrder order();

    /**
     * Start a new excerpt in the Chronicle.
     *
     * @param capacity minimum capacity to allow for.
     */
    void startExcerpt(int capacity);

    /**
     * Finish a record.  The record is not available until this is called.
     * <p/>
     * When the method is called the first time, the excerpt is shrink wrapped to the size actually used. i.e. where the position is.
     */
    void finish();

    /**
     * @return a wrapper for this excerpt as an InputStream
     */
    InputStream inputStream();

    /**
     * @return a wrapper for this excerpt as an OutputStream
     */
    OutputStream outputStream();

    /**
     * Write an enumerated type.  Here enumerated has a general sense.
     * Its requirements are; <ol>
     * <li>values must have one to one mapping with String as the reverse</li>
     * <li>the values must be immutable</li>
     * <li>the class must have a valueOf(String) or Constructor(String) which can take the output of toString(). </li>
     * </ol>
     * <p/>
     * This method has special handling for Enum, Classes and Strings. It can handle other types which comply e.g. BigInteger
     * <p/>
     * If the reader cannot be expect to know the type, the class can be written (and read) first.
     */
    <E> void writeEnum(E e);

    /**
     * Read what was written with writeEnum
     */
    <E> E readEnum(Class<E> eClass);

    /**
     * Write a collection of enumerated values.
     */
    <E> void writeEnums(Collection<E> eList);

    /**
     * Write a map or enumerated keys and values.
     */
    <K, V> void writeMap(Map<K, V> map);

    /**
     * Reads a collection of enumerated types.
     */
    <E> List<E> readEnums(Class<E> eClass);

    /**
     * Reads a map of key/values with known enumerated types.
     */
    <K, V> Map<K, V> readMap(Class<K> kClass, Class<V> vClass);
}
