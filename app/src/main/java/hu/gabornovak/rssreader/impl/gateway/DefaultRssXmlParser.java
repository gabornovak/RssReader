package hu.gabornovak.rssreader.impl.gateway;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import hu.gabornovak.rssreader.logic.gateway.RssXmlParser;
import hu.gabornovak.rssreader.logic.model.RssItem;

/**
 * Based on the official android xml parser:
 * https://developer.android.com/training/basics/network-ops/xml.html
 * <p>
 * Created by gnovak on 7/28/2016.
 */

public class DefaultRssXmlParser implements RssXmlParser {
    private static final String ns = null;

    @Override
    public List<RssItem> parse(InputStream in) {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            parser.nextTag();
            return readFeed(parser);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private List<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<RssItem> entries = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "channel");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("item")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private RssItem readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String description = null;
        String link = null;
        String pubDate = null;
        String imageUrl = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "title":
                    title = readTitle(parser);
                    break;
                case "description":
                    description = readDescription(parser);
                    break;
                case "link":
                    link = readLink(parser);
                    break;
                case "pubDate":
                    pubDate = readPubDate(parser);
                    break;
                case "enclosure":
                    imageUrl = readEnclosure(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new RssItem(title, description, link, pubDate, imageUrl);
    }

    private String readEnclosure(XmlPullParser parser) throws IOException, XmlPullParserException {
        String image = "";
        parser.require(XmlPullParser.START_TAG, ns, "enclosure");
        String tag = parser.getName();
        String relType = parser.getAttributeValue(null, "type");
        if (tag.equals("enclosure")) {
            if (relType.equals("image/jpeg")) {
                image = parser.getAttributeValue(null, "url");
                parser.nextTag();
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "enclosure");
        return image;
    }

    private String readPubDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        return readSimpleTag(parser, "pubDate");
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        return readSimpleTag(parser, "title");
    }

    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        return readSimpleTag(parser, "link");
    }

    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        return readSimpleTag(parser, "description");
    }

    private String readSimpleTag(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String text = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return text;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
