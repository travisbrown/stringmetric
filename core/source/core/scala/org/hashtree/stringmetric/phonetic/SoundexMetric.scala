package org.hashtree.stringmetric.phonetic

import org.hashtree.stringmetric.{ StringCleaner, StringCleanerDelegate, StringMetric }

/** An implementation of the Soundex [[org.hashtree.stringmetric.StringMetric]]. */
object SoundexMetric extends StringMetric {
	override def compare(charArray1: Array[Char], charArray2: Array[Char])(implicit stringCleaner: StringCleaner): Option[Boolean] = {
		val ca1 = stringCleaner.clean(charArray1)
		val ca2 = stringCleaner.clean(charArray2)

		if (ca1.length == 0 || ca2.length == 0) None
		else {
			val se1 = Soundex.compute(ca1)
			val se2 = Soundex.compute(ca2)

			if (!se1.isDefined || !se2.isDefined || (se1.get.length == 0 && se2.get.length == 0))
				None
			else
				Some(se1.get.sameElements(se2.get))
		}
	}

	override def compare(string1: String, string2: String)(implicit stringCleaner: StringCleaner): Option[Boolean] = {
		// Unable to perform simple equality check, due to situations where no letters are passed.
		compare(
			stringCleaner.clean(string1.toCharArray),
			stringCleaner.clean(string2.toCharArray)
		)(new StringCleanerDelegate)
	}
}