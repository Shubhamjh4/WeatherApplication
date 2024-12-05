import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;
    private JLabel temperatureText;
    private JLabel weatherConditionDesc;
    private JLabel humidityText;
    private JLabel windspeedText;
    private JLabel weatherConditionImage; // Moved this to instance variable

    public WeatherAppGui() {
        // Setup GUI and add a title
        super("Weather App");

        // Configure GUI to end the program's process once it has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set the size of our GUI (in pixels)
        setSize(450, 650);

        // Load our GUI at the center of the screen
        setLocationRelativeTo(null);

        // Make our layout manager null to manually position our components within the GUI
        setLayout(null);

        // Prevent any resize of our GUI
        setResizable(false);

        addGuiComponents();

        // Fetch weather data for default location
        weatherData = WeatherApp.getWeatherData("Mumbai");
        updateGuiWithWeatherData();
    }

    private void addGuiComponents() {
        // Search field
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15, 15, 351, 45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);

        // Weather image
        weatherConditionImage = new JLabel(loadImage("D:\\WeatherAppGUI\\assets\\cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        // Temperature text
        temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        // Weather condition description
        weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 405, 450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        // Humidity image
        JLabel humidityImage = new JLabel(loadImage("D:\\WeatherAppGUI\\assets\\humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);

        // Humidity text
        humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        // Windspeed image
        JLabel windspeedImage = new JLabel(loadImage("D:\\WeatherAppGUI\\assets\\windspeed.png"));
        windspeedImage.setBounds(220, 500, 74, 66);
        add(windspeedImage);

        // Windspeed text
        windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);

        // Search button
        JButton searchButton = new JButton(loadImage("D:\\WeatherAppGUI\\assets\\search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get location from user
                String userInput = searchTextField.getText();

                // Validate input - remove whitespace to ensure non-empty text
                if (userInput.trim().isEmpty()) {
                    return;
                }

                // Retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);
                updateGuiWithWeatherData();
            }
        });
        add(searchButton);
    }

    private void updateGuiWithWeatherData() {
        // Ensure that weatherData is not null
        if (weatherData != null) {
            // Update weather image
            String weatherCondition = (String) weatherData.get("weather_condition");
            switch (weatherCondition) {
                case "Clear":
                    weatherConditionImage.setIcon(loadImage("D:\\WeatherAppGUI\\assets\\clear.png"));
                    break;
                case "Cloudy":
                    weatherConditionImage.setIcon(loadImage("D:\\WeatherAppGUI\\assets\\cloudy.png"));
                    break;
                case "Rain":
                    weatherConditionImage.setIcon(loadImage("D:\\WeatherAppGUI\\assets\\rain.png"));
                    break;
                case "Snow":
                    weatherConditionImage.setIcon(loadImage("D:\\WeatherAppGUI\\assets\\snow.png"));
                    break;
            }

            // Update temperature text
            double temperature = (double) weatherData.get("temperature");
            temperatureText.setText(temperature + " C");

            // Update weather condition text
            weatherConditionDesc.setText(weatherCondition);

            // Update humidity text
            long humidity = (long) weatherData.get("humidity");
            humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

            // Update windspeed text
            double windspeed = (double) weatherData.get("windspeed");
            windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");
        }
    }

    // Used to create images in our GUI components
    private ImageIcon loadImage(String resourcePath) {
        try {
            // Read the image file from the path given
            BufferedImage image = ImageIO.read(new File(resourcePath));
            // Returns an image icon so that our component can render it
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;
    }
}
