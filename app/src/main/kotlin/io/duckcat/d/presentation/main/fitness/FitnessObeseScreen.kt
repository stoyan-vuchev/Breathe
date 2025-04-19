package io.duckcat.d.presentation.main.fitness

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.duckcat.d.R
import io.duckcat.d.core.ui.theme.BreathDefaultColors
import io.duckcat.d.core.ui.theme.BreathTheme
import io.duckcat.d.presentation.fitness.FitnessScreenState
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

@Composable
fun FitnessObeseScreen(
    fitnessState: FitnessScreenState,
    onEditClick: () -> Unit // callback when edit button is pressed
)  {
    val bmi = fitnessState.bmi
    val setupDate = fitnessState.setupDate

    // Calculate day number (if setupDate is set, otherwise default to 1)
    val currentTime = System.currentTimeMillis()
    val dayNumber = if (setupDate > 0) {
        ((currentTime - setupDate) / TimeUnit.DAYS.toMillis(1)) + 1
    } else {
        1L
    }

    // Determine the current meal label based on current hour
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val meal = when {
        currentHour < 11 -> "Breakfast"
        currentHour < 16 -> "Lunch"
        else -> "Dinner"
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image fills the screen.
        Image(
            painter = painterResource(id = R.drawable.fitness_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Scrollable content overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 100.dp, bottom = 70.dp, start = 28.dp, end = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Changed to Top to let content scroll naturally
        ) {
            // Top header
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Fitness Assist",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White.copy(alpha = 0.5f))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Helps you know your fitness results and gives suggestions",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 17.sp
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            // BMI Condition Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End

                ){
                    // Edit button placed at the top right
                    IconButton(onClick = onEditClick) {
                        Image(
                            painter = painterResource(id = R.drawable.edit_pensil_enhanced),
                            contentDescription = "edit",
                            modifier = Modifier.size(24.dp) // adjust size as needed
                        )

                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Obese",
                        style = BreathTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text(
                        text = "BMI: ${bmi.roundToInt()}",
                        style = BreathTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Day: $dayNumber",
                        style = BreathTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(26.dp)
                    .clip(RoundedCornerShape(50)),
                color = BreathDefaultColors.background
            )

            // Workout and Diet Sections
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Diet Pro Tips ✨",
                    style = BreathTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Workout Plan Box with increased height and scrollable content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .border(2.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(16.dp)) // Add border here
                        .padding(20.dp)
                ){
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        if (dayNumber == 1L) {
                            Text(
                                text = """
                                    1. Start slow and gradually increase the intensity or repetitions as your body adapts. 
                                    2. Hydrate properly before, during, and after workouts. 
                                    3. Focus on form rather than speed or weight to prevent injury. 
                                    4. Rest properly: Get enough sleep and allow muscles to recover. 
                                    5. Listen to your body: If you feel pain (not discomfort), stop and reassess your form or 
                                    take a rest day.
                            """.trimIndent(),
                                style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                fontSize = 16.sp
                            )
                        } else {
                            Text(
                                text = "",
                                style = BreathTheme.typography.bodyMedium.copy(color = Color.White)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Day $dayNumber workout",
                    style = BreathTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Workout Plan Box with increased height and scrollable content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(20.dp)
                ) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        if (dayNumber == 1L) {
                            Text(
                                text = """Day 1: Cardio + Full Body Strength 
• Warm-up (5-10 minutes): Jumping jacks, marching in place, or light jogging. 
• Cardio (20 minutes): 
o High knees (3 sets of 1 minute) 
o Marching in place with arm raises (3 sets of 1 minute) 
o Step touches (3 sets of 1 minute) 
• Strength (20 minutes): 
o Squats (3 sets of 12-15) 
o Push-ups (3 sets of 8-12) 
o Glute bridges (3 sets of 12-15) 
o Plank (hold for 30-45 seconds)
                            """.trimIndent(),
                                style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                fontSize = 15.sp
                            )
                        } else {
                            Text(
                                text = "Workout plan for Day $dayNumber will be updated soon.",
                                style = BreathTheme.typography.bodyMedium.copy(color = Color.White)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Diet for $meal:",
                    style = BreathTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Diet Plan Box with increased height and scrollable content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(20.dp)
                ) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        when (meal) {
                            "Breakfast" -> {
                                Text(
                                    text = """
                                        Mid-Morning Snack: 
                                        • 1 apple or banana 
                                        • A small handful of walnuts or almonds
                                        
                        Breakfast: 
• Oats with Berries and Almonds 
o ½ cup rolled oats 
o 1 cup unsweetened almond milk or water 
o 1 tbsp chia seeds 
o A handful of mixed berries (blueberries, raspberries) 
o 10 almonds (or a handful of nuts) 
• Green Tea or Black Coffee (without sugar)
                                """.trimIndent(),
                                    style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                    fontSize = 12.sp
                                )
                            }
                            "Lunch" -> {
                                Text(
                                    text = """
                        Lunch: 
• Grilled Chicken Salad 
o 4 oz grilled chicken breast 
o Mixed leafy greens (spinach, lettuce, arugula) 
o Cherry tomatoes, cucumbers, bell peppers 
o 1 tbsp olive oil + lemon dressing 
o A sprinkle of seeds (pumpkin, sunflower)
                                """.trimIndent(),
                                    style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                    fontSize = 15.sp
                                )
                            }
                            else -> { // Dinner
                                Text(
                                    text = """
                       Dinner: 
• Baked Salmon with Steamed Veggies 
o 4-5 oz baked salmon 
o Roasted vegetables (carrots, broccoli, cauliflower) 
o ½ cup quinoa or brown rice 
o A small mixed green salad with olive oil and lemon dressing
                                """.trimIndent(),
                                    style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Recommended snack items",
                    style = BreathTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Workout Plan Box with increased height and scrollable content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(20.dp)
                ) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        if (dayNumber == 1L) {
                            Text(
                                text = """
                   Mid-Morning Snack: 
• 1 apple or banana 
• A small handful of walnuts or almonds.
Afternoon Snack: 
• 1 small serving of Greek yogurt (unsweetened) with a drizzle of honey or cinnamon.
Evening Snack (Optional, if hungry): 
• A handful of raw veggies (like carrots or celery) or a small portion of cottage cheese.
                            """.trimIndent(),
                                style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                fontSize = 16.sp
                            )
                        } else {
                            Text(
                                text = "will be updated soon",
                                style = BreathTheme.typography.bodyMedium.copy(color = Color.White)
                            )
                        }
                    }
                }
            }
        }
    }
}


