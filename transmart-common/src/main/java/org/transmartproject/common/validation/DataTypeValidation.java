package org.transmartproject.common.validation;

import org.transmartproject.common.type.DataType;
import org.transmartproject.common.type.Operator;

import javax.validation.Constraint;
import java.util.*;

import static org.transmartproject.common.type.DataType.*;
import static org.transmartproject.common.type.DataType.None;
import static org.transmartproject.common.type.Operator.*;

public class DataTypeValidation {

    private static final Map<DataType, Class<?>> typeClassMap = new HashMap<>();
    private static final Map<DataType, Class<?>> classForType;
    static {
        typeClassMap.put(Id, Object.class);
        typeClassMap.put(Numeric, Number.class);
        typeClassMap.put(Date, Date.class);
        typeClassMap.put(String, CharSequence.class);
        typeClassMap.put(Text, CharSequence.class);
        typeClassMap.put(Event, Constraint.class);
        typeClassMap.put(Object, Object.class);
        typeClassMap.put(Collection, Collection .class);
        typeClassMap.put(Constraint, Constraint.class);
        classForType = new EnumMap<>(typeClassMap);
    }

    static boolean supportsClass(DataType dataType, Class type) {
        return type != null && dataType != None && classForType.get(dataType).isAssignableFrom(type);
    }

    public static boolean supportsValue(DataType dataType, Object obj, Operator operator) {
        return supportsValue(dataType, obj) || (dataType != None && (operatesOnCollection(operator)
            && obj instanceof Collection && ((Collection<?>) obj).stream().allMatch(
                (Object child) -> supportsValue(dataType, child))));
    }

    public static boolean supportsValue(DataType dataType, Object obj) {
        return dataType != None && (classForType.get(dataType).isInstance(obj)
            //TODO Remove in TMT-420
            || (dataType == Numeric && obj instanceof Date)
            || (obj == null && supportsNullValue(dataType)));
    }

    private static Set<DataType> nullDataTypes = EnumSet.of(String, Text, Date);

    private static boolean supportsNullValue(DataType dataType) {
        return nullDataTypes.contains(dataType);
    }

    private static final Map<DataType, Set<Operator>> operatorsForType = new HashMap<>();
    static {
        operatorsForType.put(Id, EnumSet.of(Equals, Not_equals, In));
        operatorsForType.put(Numeric, EnumSet.of(Less_than, Greater_than, Equals, Not_equals,
            Less_than_or_equals, Greather_than_or_equals, In, Before, After, Between));
        operatorsForType.put(Date, EnumSet.of(Before, After, Between,
            Less_than_or_equals, Greather_than_or_equals));
        operatorsForType.put(String, EnumSet.of(Equals, Not_equals, Like, Contains, In));
        operatorsForType.put(Text, EnumSet.of(Equals, Not_equals, Like, Contains, In));
        operatorsForType.put(Event, EnumSet.of(Before, After, Exists));
        operatorsForType.put(Object, EnumSet.of(Equals, Not_equals, In));
        operatorsForType.put(Collection, EnumSet.of(Between));
        operatorsForType.put(Constraint, EnumSet.of(And, Or));
    }

    public static boolean supportsType(Operator operator, DataType type) {
        return type != null && operator != Operator.None && operatorsForType.get(type).contains(operator);
    }

    private static Set<Operator> nullValueOperators = EnumSet.of(Equals, Not_equals);

    static boolean supportsNullValue(Operator operator) {
        return nullValueOperators.contains(operator);
    }

    private static Set<Operator> collectionOperators = EnumSet.of(In, Between);

    public static boolean operatesOnCollection(Operator operator) {
        return collectionOperators.contains(operator);
    }

}
