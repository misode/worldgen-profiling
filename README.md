# worldgen-profiling
> Fabric mod that adds custom JFR events to profile world generation

## Creating a JFR report
1. Launch Minecraft 1.18 with [Fabric](https://fabricmc.net/use/).
2. Download the [latest release](https://github.com/misode/worldgen-profiling/releases) and add it in the `mods` folder.
3. Start by running the `/jfr start` command and generate new terrain.
4. Run `/jfr stop`. This will create a file at `.minecraft/debug/client-*.jfr`.

## Analyzing the JFR report
1. Download and install the [JDK Mission Control](https://adoptopenjdk.net/jmc) tool.
2. In Mission Control, navigate to `File > Open File...` and select the JFR report.  
![jmc_SuUZYxQXXM](https://user-images.githubusercontent.com/17352009/144514425-a71842e1-366c-4678-9465-beaed0a9e3af.png)
3. Select the `Even Browser` Page in the Outline.  
![jmc_rvRbBmPA1r](https://user-images.githubusercontent.com/17352009/144514569-9e50506b-1acf-45c5-aad5-d3427158ce6a.png)
4. Find the `Minecraft > World Generation > Feature Placement` event type, and select it.
5. Create a new page with just this event type.  
![jmc_0pSlSrsXNA](https://user-images.githubusercontent.com/17352009/144514802-ca24340e-84f2-4213-9465-4a8fba4d9cf6.png)
6. Because the JFR file has an event for each feature placement per chunk, it is useful to group events.  
![jmc_di6ct6Eoin](https://user-images.githubusercontent.com/17352009/144514943-a6069315-49ad-4db7-88d7-6f1b10738361.png)
![jmc_HvCkGq9xMT](https://user-images.githubusercontent.com/17352009/144515033-db20ffff-3171-43b1-b979-57164ea2007a.png)
7. In this view, you can show average and total durations. It is now really easy to see which features are the bottlenecks.  
![jmc_FwmZ5h4ZDg](https://user-images.githubusercontent.com/17352009/144515176-afe85862-ecd1-4e36-b5c9-9baf343a230c.png)
![jmc_DxJRDkXVTw](https://user-images.githubusercontent.com/17352009/144515232-91e7f84f-c535-406f-936a-6d0973fefb71.png)
