# Mystical Customization: Reconfigured

![Mystical Customization: Reconfigured](src/main/resources/assets/mysticalcustomization/logo.png)

An unofficial port of Mystical Customization to Minecraft 26.3 and NeoForge.

## Overview

Mystical Customization: Reconfigured allows players and creators to extend and customize Mystical Agriculture using JSON configuration files, without writing Java code.

This port focuses on Minecraft 26.3 compatibility while preserving the original mod's identity and functionality.

## Features

- Create custom crops, seeds, and essences.
- Define custom crop tiers and crop types.
- Modify existing crops and crop properties.
- Add and modify mob soul types.
- Configure or disable augments.

Configuration files are located in `config/mysticalcustomization/`. Registry-based configuration changes generally require restarting Minecraft.

## Port Changes

- Updated to Minecraft 26.3, NeoForge, and Java 25.
- Migrated Minecraft and NeoForge APIs and configuration loaders.
- Updated custom crop model handling for Minecraft 26.3.
- Added compatibility with Cucumber: Re-Pickled and Mystical Agriculture: Replanted.
- Updated the Gradle build and local Maven dependency management.
- Preserved the original mod ID and Java package structure.
- Retained optional Mystical Agradditions integration without requiring it at compile time.

Custom crops, crop tiers, crop types, mob soul customization, and augment configuration were tested during port development. Dedicated server compatibility has not yet been verified.

## Downloads

- [Mystical Customization: Reconfigured on CurseForge](https://www.curseforge.com/minecraft/mc-mods/mystical-customization-reconfigured)
- [Cucumber Library: Re-Pickled on CurseForge](https://www.curseforge.com/minecraft/mc-mods/cucumber-library-re-pickled)
- [Mystical Agriculture: Replanted on CurseForge](https://www.curseforge.com/minecraft/mc-mods/mystical-agriculture-replanted)

## Requirements

| Requirement | Version |
| --- | --- |
| Minecraft | 26.3 |
| Mod Loader | NeoForge |
| NeoForge | 26.3.0.115 or newer compatible version |
| Java | 25 |
| Cucumber: Re-Pickled | 0.1.0 or newer compatible version |
| Mystical Agriculture: Replanted | 0.1.0 or newer compatible version |

Mystical Agradditions is optional. Use the unofficial 26.3 ports rather than the original versions intended for older Minecraft releases.

## Building from Source

### Prerequisites

- JDK 25
- Git
- Internet connection for Gradle dependencies

Run the following steps from a workspace containing the three repositories as sibling directories. The explicit destination folder names below preserve the Maven artifact IDs used by the dependency builds.

### 1. Build Cucumber: Re-Pickled

```bash
git clone --branch 26.3 https://github.com/Kiyoshibara/Cucumber-Re-Pickled.git Cucumber
cd Cucumber
```

Windows:

```cmd
gradlew.bat clean build publishToMavenLocal
```

Linux / macOS:

```bash
./gradlew clean build publishToMavenLocal
```

### 2. Build Mystical Agriculture: Replanted

```bash
cd ..
git clone --branch 26.3 https://github.com/Kiyoshibara/Mystical-Agriculture-Replanted.git MysticalAgriculture
cd MysticalAgriculture
```

Windows:

```cmd
gradlew.bat clean build publishToMavenLocal
```

Linux / macOS:

```bash
./gradlew clean build publishToMavenLocal
```

Both dependencies are now published to your local Maven repository.

### 3. Build Mystical Customization: Reconfigured

```bash
cd ..
git clone --branch 26.3 https://github.com/Kiyoshibara/Mystical-Customization-Reconfigured.git MysticalCustomization
cd MysticalCustomization
```

Windows:

```cmd
gradlew.bat clean build
```

Linux / macOS:

```bash
./gradlew clean build
```

The standard mod JAR and sources JAR are generated in `build/libs/`.

To publish this project to your local Maven repository, run `gradlew.bat publishToMavenLocal` on Windows or `./gradlew publishToMavenLocal` on Linux / macOS.

Maven coordinates: `com.blakebr0.mysticalcustomization:MysticalCustomization:0.1.0`.

## Development

- Language: Java 25
- Build System: Gradle
- Mod Loader: NeoForge
- Recommended IDE: IntelliJ IDEA

The original mod ID (`mysticalcustomization`) and Java package structure are intentionally preserved.

## Versioning

This port uses independent version numbering. The initial port release is `0.1.0`, independent of the original Mystical Customization release numbering.

## Original Project

Mystical Customization was originally created by BlakeBr0.

- [Original GitHub Repository](https://github.com/BlakeBr0/MysticalCustomization)
- [Original CurseForge Project](https://www.curseforge.com/minecraft/mc-mods/mystical-customization)

### Upstream Source

- Original branch: `26.1`
- Original mod version: `7.0.0`
- Git history baseline: [`2d93cb1560ac916d7c0a1a0766adc75b445ff4bd`](https://github.com/BlakeBr0/MysticalCustomization/commit/2d93cb1560ac916d7c0a1a0766adc75b445ff4bd)

## Credits

- **BlakeBr0** — Original creator and developer.
- **Original contributors** — Contributions to the original Mystical Customization project.
- **Kiyoshibara** — Unofficial Minecraft 26.3 port and maintenance.

This is an independent, unofficial port. It is not affiliated with, endorsed by, or maintained by the original developer.

## License

This project is licensed under the MIT License.

Original copyright © 2020 BlakeBr0.

The original copyright and license notices are preserved. See [LICENSE](LICENSE) for the full license text.

## Issues and Support

- [Report an Issue](https://github.com/Kiyoshibara/Mystical-Customization-Reconfigured/issues)

Please report issues specific to this port here, not to the original Mystical Customization repository.

## Contact

Development inquiries: kiyoshibaradev@gmail.com

## Support Development

If you enjoy this port and would like to support continued development and future updates, you can support me on Patreon.

❤️ **Patreon:** [Kiyoshibara](https://www.patreon.com/c/Kiyoshibara)