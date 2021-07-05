package de.sth.minesweeper.updates;

import de.sth.minesweeper.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Objects;

import static de.sth.minesweeper.updates.UpdateConstants.UpdateOptions.*;

public class Update {
    final UpdateConstants.UpdateOptions option;
    final boolean ea;
    final URI uri;
    final String version;

    public Update(UpdateConstants.UpdateOptions option, boolean isEA, String version) {
        this.option = option;
        this.ea = isEA;
        this.version = version;
        URI tempURI;
        try {
            if (this.version == null) throw new NullPointerException();
            tempURI = new URI("https://github.com/SebastianThomas/minesweeper/releases/" + version);
        } catch (NullPointerException | URISyntaxException ignored) {
            tempURI = null;
        }
        this.uri = tempURI;
    }

    public static Update getUpdateOptions() {
        Logger.getInstance().log("Checking for updates...");

        JSONArray res = getResponse();
        if (res == null) {
            System.out.println("Something went wrong (do you have an internet connection?)");
            Logger.getInstance().log("No internet connection found?");
            return null;
        }

        String latestRelease = getLatestRelease(res);
        String latestEARelease = getLatestEARelease(res);

        Logger.getInstance().log("Current release: " + UpdateConstants.currentVersion);
        Logger.getInstance().log("Found latest release: " + latestRelease + "; latest EA release: " + getLatestEARelease(res));

        if (getLatestEARelease(res).equals(UpdateConstants.currentVersion)) {
            System.out.println("Have latest release (might be an Early Access version)");
            return new Update(UP_TO_DATE, isEA(UpdateConstants.currentVersion), null);
        } else if (Objects.equals(getLatestRelease(res) + "-ea", UpdateConstants.currentVersion)) {
            // For example you have "v2.0-ea" and "v2.0" is latest and available
            System.out.println("The full update for your EA-preview is available!");
            return new Update(UPDATE_FROM_EA_TO_FULL, false, getLatestRelease(res));
        } else if (Objects.equals(getLatestRelease(res), UpdateConstants.currentVersion)) {
            // Have the latest full release
            if (!Objects.equals(getLatestRelease(res), getLatestEARelease(res))) {
                System.out.println("THERE IS ANOTHER Early Access RELEASE");
                return new Update(UPDATE_TO_NEW_EA, true, getLatestEARelease(res));
            } else {
                System.out.println("Have latest release");
                return new Update(UP_TO_DATE_WITHOUT_EA, false, null);
            }
        } else if (getReleaseMajorVersion(latestRelease) > getReleaseMajorVersion(UpdateConstants.currentVersion)) {
            // Major patch available
            System.out.println("New MAJOR patch");
            return new Update(UPDATE_TO_MAJOR, false, getLatestRelease(res));
        } else if (getReleaseMinorVersion(latestRelease) > getReleaseMinorVersion(UpdateConstants.currentVersion)) {
            // Minor patch available
            System.out.println("New MINOR patch");
            return new Update(UPDATE_TO_MINOR, false, getLatestRelease(res));
        } else if (getReleasePatchVersion(latestRelease) > getReleasePatchVersion(UpdateConstants.currentVersion)) {
            System.out.println("New patch available");
            return new Update(UPDATE_TO_PATCH, false, getLatestRelease(res));
        } else if (getReleasePatchVersion(latestEARelease) > getReleasePatchVersion(UpdateConstants.currentVersion) && isEA(UpdateConstants.currentVersion)) {
            System.out.println("New EA patch available");
            return new Update(UPDATE_TO_PATCH, false, getLatestEARelease(res));
        } else {
            System.out.println(latestRelease);
            System.out.println(UpdateConstants.currentVersion);
            System.out.println(getReleasePatchVersion(latestRelease));
            System.out.println(getReleasePatchVersion(UpdateConstants.currentVersion));

            // Problem ??
            System.out.println("Are you in development? Otherwise report the following as a bug (https://github.com/SebastianThomas/minesweeper/issues/new):");
            System.out.println("Latest version: " + getLatestEARelease(res) + "; Current version: " + UpdateConstants.currentVersion + "; Time: " + new Date().getTime());
            return new Update(HAVE_NEWER_VERSION, true, null);
        }
    }

    public static int getReleaseMajorVersion(String version) {
        // "vXXX.YYY.ZZZ"
        return Integer.parseInt(version.substring(1, version.indexOf('.')));
    }

    public static int getReleaseMinorVersion(String version) {
        System.out.println(version);

        int firstIndex = version.indexOf('.');
        int secondIndex = version.substring(firstIndex + 1).indexOf('.');

        if (secondIndex == -1) secondIndex = version.length();
        else secondIndex += firstIndex + 1;
        // "vXXX.YYY.ZZZ"
        return Integer.parseInt(version.substring(firstIndex + 1, secondIndex));
    }

    public static int getReleasePatchVersion(String version) {
        int firstIndex = version.indexOf('.');
        int secondIndex = version.substring(firstIndex + 1).indexOf('.');
        int thirdIndex = isEA(version) ? version.indexOf('-') : version.length();
        if (secondIndex == -1) return 0;
        secondIndex += firstIndex + 1;
        int patchVersion = Integer.parseInt(version.substring(secondIndex + 1, thirdIndex));
        System.out.println(patchVersion);
        return patchVersion;
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
            return null;
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
