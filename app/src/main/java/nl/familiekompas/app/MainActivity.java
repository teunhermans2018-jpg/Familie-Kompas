
package nl.familiekompas.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.*;
import android.view.ViewGroup;

public class MainActivity extends Activity {
    private LinearLayout content;
    private final int green = Color.rgb(23, 74, 56);
    private final int pale = Color.rgb(239, 247, 242);

    private final String[] sections = {
        "Familieoverzicht",
        "Agenda & planning",
        "Taken & actiepunten",
        "Gezinsfinanciën",
        "Familiegegevens",
        "Notities & afspraken"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHome();
    }

    private int dp(int value) {
        return (int) (value * getResources()
            .getDisplayMetrics().density);
    }

    private void showHome() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.rgb(250, 252, 250));

        TextView header = new TextView(this);
        header.setText("FamilieKompas");
        header.setTextSize(25);
        header.setTypeface(null, Typeface.BOLD);
        header.setTextColor(Color.WHITE);
        header.setBackgroundColor(green);
        header.setPadding(dp(20), dp(24), dp(20), dp(24));
        root.addView(header);

        ScrollView scroll = new ScrollView(this);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(16), dp(16), dp(16), dp(16));

        TextView intro = new TextView(this);
        intro.setText("Alles voor jullie familie op één plek.");
        intro.setTextSize(18);
        intro.setTextColor(green);
        intro.setPadding(0, 0, 0, dp(16));
        content.addView(intro);

        for (String section : sections) {
            Button button = new Button(this);
            button.setText(section);
            button.setAllCaps(false);
            button.setTextColor(green);
            button.setBackgroundColor(pale);
            button.setOnClickListener(v -> openSection(section));

            LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = dp(8);
            content.addView(button, params);
        }

        TextView note = new TextView(this);
        note.setText(
            "Eerste versie: notities worden op dit toestel bewaard. " +
            "Gezamenlijk delen en Google-koppelingen moeten later " +
            "nog veilig worden ingesteld.");
        note.setTextSize(13);
        note.setTextColor(Color.DKGRAY);
        note.setPadding(0, dp(12), 0, dp(12));
        content.addView(note);

        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));

        setContentView(root);
    }

    private void openSection(String section) {
        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(18), dp(18), dp(18), dp(18));
        page.setBackgroundColor(Color.rgb(250, 252, 250));

        TextView title = new TextView(this);
        title.setText(section);
        title.setTextSize(23);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(green);
        page.addView(title);

        EditText editor = new EditText(this);
        editor.setGravity(Gravity.TOP);
        editor.setHint("Schrijf hier notities en afspraken…");
        editor.setMinLines(8);

        String key = "section_" + section;
        editor.setText(getSharedPreferences(
            "familiekompas", Context.MODE_PRIVATE)
            .getString(key, ""));
        page.addView(editor, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(240)));

        Button save = new Button(this);
        save.setText("Notities opslaan");
        save.setAllCaps(false);
        save.setOnClickListener(v -> {
            getSharedPreferences("familiekompas",
                Context.MODE_PRIVATE).edit()
                .putString(key, editor.getText().toString())
                .apply();
            Toast.makeText(this, "Opgeslagen op dit toestel",
                Toast.LENGTH_SHORT).show();
        });
        page.addView(save);

        Button back = new Button(this);
        back.setText("Terug naar overzicht");
        back.setAllCaps(false);
        back.setOnClickListener(v -> showHome());
        page.addView(back);

        setContentView(page);
    }
}
