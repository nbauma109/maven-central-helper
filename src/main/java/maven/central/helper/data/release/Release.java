package maven.central.helper.data.release;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import maven.central.helper.model.response.docs.Docs;

public class Release {
    private String version;
    private LocalDate date;

    public Release(String version, LocalDate date) {
        this.version = version;
        this.date = date;
    }

    public Release(Docs doc) {
        this(doc.getVersion(), LocalDate.ofEpochDay(TimeUnit.MILLISECONDS.toDays(doc.getTimestamp())));
    }

    @Override
    public String toString() {
        return String.join(" - ", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)), version);
    }
}
