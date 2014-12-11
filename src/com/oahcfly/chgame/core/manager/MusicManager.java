
package com.oahcfly.chgame.core.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

public class MusicManager implements Disposable {

    ObjectMap<String, Music> musics = new ObjectMap<String, Music>();

    float musicVolume = 1f;

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setVolume(float volume) {
        for (Music music : musics.values()) {
            music.setVolume(volume);
        }
        this.musicVolume = volume;
    }

    public void stopMusic(String res) {
        final Music musicFromPool = musics.get(res);
        if (null == musicFromPool) {
            final Music musicFromAssets = Gdx.audio.newMusic(Gdx.files.internal(res));
            musics.put(res, musicFromAssets);
            musicFromAssets.stop();
        } else {
            musicFromPool.stop();
        }
    }

    public void pauseMusic(String res) {
        final Music musicFromPool = musics.get(res);
        if (null == musicFromPool) {
            final Music musicFromAssets = Gdx.audio.newMusic(Gdx.files.internal(res));
            musics.put(res, musicFromAssets);
            if (musicFromAssets.isPlaying()) {
                musicFromAssets.pause();
            }
        } else {
            if (musicFromPool.isPlaying()) {
                musicFromPool.pause();
            }
        }
    }

    public void playMusic(String res, boolean loop) {
        final Music musicFromPool = musics.get(res);
        if (null == musicFromPool) {
            final Music musicFromAssets = Gdx.audio.newMusic(Gdx.files.internal(res));
            musics.put(res, musicFromAssets);
            this.playMusic(musicFromAssets, loop);
        } else {
            this.playMusic(musicFromPool, loop);
        }
    }

    private void playMusic(Music music, boolean loop) {
        if (!music.isPlaying()) {
            music.setVolume(musicVolume);
            music.setLooping(true);
            music.play();
        }

    }

    public void dispose() {
        for (Music music : musics.values()) {
            if (null != music)
                music.dispose();
        }
        this.musics.clear();
        this.musicVolume = 1.0f;
    }

}
