package lombok

import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.CONSTRUCTOR
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.annotation.AnnotationTarget.FUNCTION

/** Duplicates Lombok's `@Generated` to aid Jacoco. */
@Target(CONSTRUCTOR, FUNCTION, FIELD, CLASS)
annotation class Generated
