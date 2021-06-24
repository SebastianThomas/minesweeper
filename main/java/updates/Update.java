package updates;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Objects;

public class Update {
    public static void main(String... args) {
        System.out.println("Checking for updates...");

        JSONArray res = getResponse();

        String latestRelease = getLatestRelease(res);

        if (Objects.equals(getLatestEARelease(res), UpdateConstants.currentVersion)) {
            System.out.println("Have latest release (might be an Early Access version)");
        } else if (Objects.equals(getLatestRelease(res) + "-ea", UpdateConstants.currentVersion)) {
            // For example you have "v2.0-ea" and "v2.0" is latest and available
            System.out.println("The full update for your EA-preview is available!");
        } else if (Objects.equals(getLatestRelease(res), UpdateConstants.currentVersion)) {
            // Have the latest full release
            if (!Objects.equals(getLatestRelease(res), getLatestEARelease(res))) {
                System.out.println("BUT THERE IS ANOTHER Early Access RELEASE");
            } else {
                System.out.println("Have latest release");
            }
        } else if (getReleaseMajorVersion(latestRelease) > getReleaseMajorVersion(UpdateConstants.currentVersion)) {
            // Major patch available
            System.out.println("New MAJOR patch");
        } else if (getReleaseMinorVersion(latestRelease) > getReleaseMinorVersion(UpdateConstants.currentVersion)) {
            // Minor patch available
            System.out.println("New MINOR patch");
        } else {
            // Problem ??
            System.out.println("Are you in development? Otherwise report the following as a bug (https://github.com/SebastianThomas/minesweeper/issues/new):");
            System.out.println("Latest version: " + latestRelease + "; Current version: " + UpdateConstants.currentVersion + "; Time: " + new Date().getTime());
        }
    }

    public static int getReleaseMajorVersion(String version) {
        // "vXXX.YYY.ZZZ"
        return Integer.parseInt(version.substring(1, version.indexOf('.')));
    }

    public static int getReleaseMinorVersion(String version) {
        int firstIndex = version.indexOf('.');
        int secondIndex = version.substring(firstIndex + 1).indexOf('.') + firstIndex;
        if (isEA(version)) secondIndex = version.indexOf('-');
        // "vXXX.YYY.ZZZ"
        return Integer.parseInt(version.substring(firstIndex + 1, secondIndex == firstIndex - 1 ? version.length() : secondIndex));
    }

    public static JSONArray getResponse() {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.github.com/repos/SebastianThomas/minesweeper/releases"))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONArray(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Did not work");
        }
    }

    public static String getLatestRelease(JSONArray arr) {
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(0);
            String v = obj.getString("tag_name");
            if (!isEA(v)) return v;
        }
        return "v2.0";
    }

    public static String getLatestEARelease(JSONArray arr) {
        return arr.getJSONObject(0).getString("tag_name");
    }

    public static boolean isEA(String versionNr) {
        return versionNr.contains("ea") || versionNr.contains("alpha") || versionNr.contains("beta");
    }
}
