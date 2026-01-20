# MowzieOverhauledMusicCompat

Client-side compat mod for **Forge 1.20.1**.

## What it does
- **Disables Mowzie's Mobs boss themes** (Frostmaw, Ferrous Wroughtnaut, Umvuthi, Sculptor).
- Plays your custom boss tracks with **fade in / fade out**.
- Smoothly **ducks any other MUSIC sounds** while a boss is active (OverhauledMusic included) so you don't get overlap.

Notes:
- "Ducking" fades other music down and back up. It cannot truly pause and resume a track at the exact timestamp.
- This mod does not do Grottol (as requested).

## Boss IDs used
- `mowziesmobs:frostmaw`
- `mowziesmobs:ferrous_wroughtnaut`
- `mowziesmobs:umvuthi`
- `mowziesmobs:sculptor`

## Audio is now a separate resource pack
To keep the mod jar small and reduce load-time stutter, the boss tracks are **NOT** bundled in the mod anymore.

Enable the companion resource pack (zip) that contains:
- `assets/mowzieomcompat/sounds.json`
- `assets/mowzieomcompat/sounds/music/*.ogg`

Sound events:
- `mowzieomcompat:boss_frostmaw`
- `mowzieomcompat:boss_wroughtnaut`
- `mowzieomcompat:boss_umvuthi`
- `mowzieomcompat:boss_sculptor`

## Building
### On GitHub Actions
Just push this repo. The workflow builds automatically and uploads the jar artifact.

### Locally
Install Java 17 + Gradle, then run:
`gradle clean build`

Output jar:
`build/libs/`
