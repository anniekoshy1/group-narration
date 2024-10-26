package com.narration;

import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.DescribeVoicesRequest;
import software.amazon.awssdk.services.polly.model.DescribeVoicesResponse;
import software.amazon.awssdk.services.polly.model.OutputFormat;
import software.amazon.awssdk.services.polly.model.PollyException;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechResponse;
import software.amazon.awssdk.services.polly.model.Voice;

public class Narriator {
    
    private Narriator() {}

    /**
     * Narrates any given text.
     * @param text The text to be narrated.
     */
    public static void playSound(String text) {
        try (PollyClient polly = PollyClient.builder().region(Region.EU_WEST_3).build()) {
            talkPolly(polly, text);
        } catch (PollyException e) {
            System.err.println("PollyException: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Narrates a feedback message after a question or assessment.
     * @param score The score to narrate.
     */
    public static void playFeedback(double score) {
        String feedback = score >= 80 ? "Great job! You've scored " + score + "%. Moving to the next module." :
                "You scored " + score + "%. You'll need to review this module.";
        playSound(feedback);
    }

    /**
     * Reads a question aloud.
     * @param question The question text.
     */
    public static void narrateQuestion(String question) {
        playSound("Question: " + question);
    }

    private static void talkPolly(PollyClient polly, String text) {
        try {
            DescribeVoicesRequest describeVoiceRequest = DescribeVoicesRequest.builder()
                    .engine("standard")
                    .build();

            DescribeVoicesResponse describeVoicesResult = polly.describeVoices(describeVoiceRequest);
            Voice voice = describeVoicesResult.voices().stream()
                    .filter(v -> v.name().equals("Miguel"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Voice not found"));

            InputStream stream = synthesize(polly, text, voice, OutputFormat.MP3);
            AdvancedPlayer player = new AdvancedPlayer(stream,
                    javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

            player.setPlayBackListener(new PlaybackListener() {});
            player.play();

        } catch (PollyException | JavaLayerException | IOException e) {
            System.err.println("Error in Polly narration: " + e.getMessage());
            System.exit(1);
        }
    }

    private static InputStream synthesize(PollyClient polly, String text, Voice voice, OutputFormat format)
            throws IOException {
        SynthesizeSpeechRequest synthReq = SynthesizeSpeechRequest.builder()
                .text(text)
                .voiceId(voice.id())
                .outputFormat(format)
                .build();

        ResponseInputStream<SynthesizeSpeechResponse> synthRes = polly.synthesizeSpeech(synthReq);
        return synthRes;
    }
}