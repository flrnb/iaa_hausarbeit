package de.nak.iaa.server.business;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import de.nak.iaa.server.entity.Student;

/**
 * Exception die geworfen wird, falls versucht wurde eine Prüfungsleistung
 * anzulegen, obwohl die fachlich nicht (mehr) zulässig ist.
 * 
 * @author flrnb
 */
public class PruefungsleistungenUpdateException extends Exception {

	private Optional<List<IllegalPruefungsleistungException>> nested = Optional.absent();

	public void addNestedException(final IllegalPruefungsleistungException exc) {
		nested = nested.transform(
				new Function<List<IllegalPruefungsleistungException>, List<IllegalPruefungsleistungException>>() {
					@Override
					public List<IllegalPruefungsleistungException> apply(List<IllegalPruefungsleistungException> input) {
						ImmutableList.builder().addAll(input).add(exc).build();
						return input;
					}
				}).or(Optional.of(Arrays.asList(exc)));
	}

	public Optional<List<IllegalPruefungsleistungException>> getNestedExceptions() {
		return nested;
	}

	public static class IllegalPruefungsleistungException extends Exception {

		private final Student student;

		private static final long serialVersionUID = -7106476650362756766L;

		public IllegalPruefungsleistungException(Student student, String msg) {
			super(msg);
			this.student = student;
		}

		public Student getStudent() {
			return student;
		}
	}

	private static final long serialVersionUID = -2554662398324014159L;

}
