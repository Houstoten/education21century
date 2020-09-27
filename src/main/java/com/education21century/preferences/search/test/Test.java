package com.education21century.preferences.search.test;

import com.education21century.preferences.search.preference.Preference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.Table;

@Indexed
@Entity
@Data
@NoArgsConstructor
@Table(name = "tests")
@SuperBuilder
public class Test extends Preference {


}
